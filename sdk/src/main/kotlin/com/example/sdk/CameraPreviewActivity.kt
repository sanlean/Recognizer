package com.example.sdk

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sdk.document.DocumentDetectionActivity


internal open class CameraPreviewActivity : AppCompatActivity() {

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

    protected fun getPreview(previewView: PreviewView) = Preview.Builder()
        .build()
        .also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

    protected fun updateError(e: Throwable, errorType: ErrorType){
        e.printStackTrace()
        cameraRequestListener?.errorProcessingImage(errorType)
        finish()
    }

    open fun create() = Unit

    internal companion object {
        internal var cameraRequestListener: CameraRequestListener? = null
        private const val REQUEST_CODE_PERMISSIONS = 33
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}