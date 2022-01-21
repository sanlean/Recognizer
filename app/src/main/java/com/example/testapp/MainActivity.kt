package com.example.testapp

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.sdk.document.DocumentDetectionActivity
import com.example.sdk.document.DocumentListener
import com.example.sdk.face.FaceDetectionActivity
import com.example.sdk.face.FaceListener
import com.example.testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), DocumentListener, FaceListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupButtons()
        hideCards()
    }

    private fun hideCards(){
        binding.cvDocument.isVisible = false
        binding.cvFace.isVisible = false
        binding.tvNoPictures.isVisible = true
    }

    private fun setupButtons(){
        binding.btnReadDocument.setOnClickListener {
            DocumentDetectionActivity.start(this, this)
        }
        binding.btnDetectFace.setOnClickListener {
            FaceDetectionActivity.start(this, this)
        }
    }

    override fun textFoundOnDocument(text: String) {
        binding.cvDocument.isVisible = true
        binding.tvNoPictures.isVisible = false
        binding.tvDocumentResult.text = text
    }

    override fun noTextFound() {
        Toast.makeText(this, R.string.text_not_found, Toast.LENGTH_LONG).show()
    }

    override fun facePictureFound(facePicture: Bitmap) {
        binding.cvFace.isVisible = true
        binding.tvNoPictures.isVisible = false
        binding.ivFace.setImageBitmap(facePicture)
    }

    override fun noFaceFound() {
        Toast.makeText(this, R.string.face_not_found, Toast.LENGTH_LONG).show()
    }

    override fun cameraPermissionDenied() {
        Toast.makeText(this, R.string.camera_permission_denied, Toast.LENGTH_LONG).show()
    }
}
