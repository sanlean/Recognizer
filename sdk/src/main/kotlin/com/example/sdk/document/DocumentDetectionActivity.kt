package com.example.sdk.document

import android.content.Context
import android.content.Intent
import androidx.camera.core.CameraSelector
import com.example.sdk.CameraPreviewActivity
import com.example.sdk.databinding.ActivityDocumentDetectionBinding

class DocumentDetectionActivity: CameraPreviewActivity() {

    private lateinit var binding: ActivityDocumentDetectionBinding

    override fun create() {
        super.create()
        binding = ActivityDocumentDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startCamera(
            previewView = binding.cameraPreview,
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        )
    }

    companion object{
        private var documentListener: DocumentListener? = null
        fun start(context: Context, documentListener: DocumentListener) {
            Companion.documentListener = documentListener
            cameraRequestListener = documentListener
            context.startActivity(Intent(context, DocumentDetectionActivity::class.java))
        }
    }
}