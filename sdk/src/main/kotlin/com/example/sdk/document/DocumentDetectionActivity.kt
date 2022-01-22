package com.example.sdk.document

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import com.example.sdk.CameraPreviewActivity
import com.example.sdk.databinding.ActivityDocumentDetectionBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class DocumentDetectionActivity : CameraPreviewActivity() {

    private lateinit var binding: ActivityDocumentDetectionBinding

    override fun create() {
        super.create()
        binding = ActivityDocumentDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startCamera(
            previewView = binding.cameraPreview,
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        )
        binding.btnBack.setOnClickListener { finish() }
        binding.btnTakePicture.setClickListener {
            takePhoto(binding.documentOverlayView.getFrameParameters(), takePhotoError, takePhotoSucces)
        }
    }

    private val takePhotoError = {
        documentListener?.noTextFound()
        finish()
    }

    private val takePhotoSucces: (InputImage, Bitmap) -> Unit = { image, bitmap ->
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                Log.d(TAG, visionText.text)
                if (visionText.isTextEmpty()){
                    documentListener?.noTextFound()
                }else{
                    documentListener?.textFoundOnDocument(visionText.text)
                }
                finish()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                documentListener?.noTextFound()
                finish()
            }
    }

    companion object {
        private const val TAG = "DocumentDetectionCamera"
        private var documentListener: DocumentListener? = null
        fun start(context: Context, documentListener: DocumentListener) {
            Companion.documentListener = documentListener
            cameraRequestListener = documentListener
            context.startActivity(Intent(context, DocumentDetectionActivity::class.java))
        }
    }
}