package com.example.sdk

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sdk.utils.rotate
import com.google.mlkit.vision.common.InputImage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


open class CameraPreviewActivity : AppCompatActivity() {
    protected var imageCapture: ImageCapture? = null
    protected lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (allPermissionsGranted()) {
            create()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS &&
            grantResults.all { result ->
                result == PackageManager.PERMISSION_GRANTED
            }
        ) {
            create()
        } else {
            cameraRequestListener?.cameraPermissionDenied()
            finish()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all { p ->
        ContextCompat.checkSelfPermission(
            baseContext, p
        ) == PackageManager.PERMISSION_GRANTED
    }

    open fun create() = Unit

    protected fun startCamera(
        previewView: PreviewView,
        cameraSelector: CameraSelector
    ) {
        setupOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    protected fun takePhoto(
        frame: FrameParameters,
        error: () -> Unit,
        success: (InputImage, Bitmap) -> Unit
    ) {
        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture?.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                @SuppressLint("UnsafeOptInUsageError")
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        val image: InputImage = InputImage.fromMediaImage(
                            mediaImage,
                            imageProxy.imageInfo.rotationDegrees
                        )
                        image.bitmapInternal?.let {
                            success(image, it)
                        }?: error()
                    }else{
                        error()
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                    error()
                }
            })

        object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                error()
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val originalPicture =
                    BitmapFactory.decodeFile(photoFile.absolutePath, BitmapFactory.Options())
                val croppedImage = originalPicture.rotate(270F)
                val fileOutputStream = FileOutputStream(photoFile)
                croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)

                val image: InputImage = InputImage.fromBitmap(croppedImage, 0)
                success(image, croppedImage)
            }
        }
    }

    private fun setupOutputDirectory() {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.photo_prefix)).apply { mkdirs() }
        }
        outputDirectory = if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir

    }

    companion object {
        private const val TAG = "CameraPreview"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        internal var cameraRequestListener: CameraRequestListener? = null
        private const val REQUEST_CODE_PERMISSIONS = 33
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}