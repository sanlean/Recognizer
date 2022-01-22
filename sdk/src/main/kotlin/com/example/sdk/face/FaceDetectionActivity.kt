package com.example.sdk.face

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.CameraSelector
import com.example.sdk.CameraPreviewActivity
import com.example.sdk.databinding.ActivityFaceDetectionBinding
import com.example.sdk.document.DocumentDetectionActivity
import com.example.sdk.document.isTextEmpty
import com.example.sdk.utils.cropBitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class FaceDetectionActivity: CameraPreviewActivity() {

    private lateinit var binding: ActivityFaceDetectionBinding

    override fun create() {
        super.create()
        binding = ActivityFaceDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startCamera(
            previewView = binding.cameraPreview,
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        )
        binding.btnBack.setOnClickListener { finish() }
        binding.btnTakePicture.setClickListener {
            takePhoto(binding.faceOverlayView.getFrameParameters(), takePhotoError, takePhotoSucces)
        }
    }

    private val takePhotoError = {
        faceListener?.noFaceFound()
        finish()
    }

    private val takePhotoSucces: (InputImage, Bitmap) -> Unit = { image, bitmap ->
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        val detector = FaceDetection.getClient(highAccuracyOpts)
        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isEmpty()){
                    faceListener?.noFaceFound()
                    finish()
                }else{
                    val croppedFace = bitmap.cropBitmap(faces[0].boundingBox)
                    faceListener?.facePictureFound(croppedFace)
                }
                finish()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                faceListener?.noFaceFound()
                finish()
            }
    }

    companion object{
        private const val TAG = "FaceDetectionCamera"
        private var faceListener: FaceListener? = null
        fun start(context: Context, faceListener: FaceListener) {
            FaceDetectionActivity.faceListener = faceListener
            cameraRequestListener = faceListener
            context.startActivity(Intent(context, FaceDetectionActivity::class.java))
        }
    }
}