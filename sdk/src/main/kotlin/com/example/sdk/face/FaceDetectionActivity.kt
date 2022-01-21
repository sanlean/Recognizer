package com.example.sdk.face

import android.content.Context
import android.content.Intent
import androidx.camera.core.CameraSelector
import com.example.sdk.CameraPreviewActivity
import com.example.sdk.databinding.ActivityFaceDetectionBinding

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
    }

    companion object{
        private var faceListener: FaceListener? = null
        fun start(context: Context, faceListener: FaceListener) {
            FaceDetectionActivity.faceListener = faceListener
            cameraRequestListener = faceListener
            context.startActivity(Intent(context, FaceDetectionActivity::class.java))
        }
    }
}