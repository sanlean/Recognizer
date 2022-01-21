package com.example.testapp

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sdk.document.DocumentDetectionActivity
import com.example.sdk.document.DocumentListener
import com.example.sdk.face.FaceDetectionActivity
import com.example.sdk.face.FaceListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DocumentListener, FaceListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_read_text.setOnClickListener {
            DocumentDetectionActivity.start(this, this)
        }

        btn_detect_face.setOnClickListener {
            FaceDetectionActivity.start(this, this)
        }
    }

    override fun textFoundOnDocument(text: String) = Unit

    override fun noTextFound() = Unit

    override fun facePictureFound(facePicture: Bitmap) = Unit

    override fun noFaceFound() = Unit

    override fun cameraPermissionDenied() {
        Toast.makeText(this, R.string.camera_permission_denied, Toast.LENGTH_LONG).show()
    }
}
