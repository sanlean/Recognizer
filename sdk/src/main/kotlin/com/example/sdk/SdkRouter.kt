package com.example.sdk

import android.content.Context
import android.content.Intent
import com.example.sdk.document.DocumentDetectionActivity
import com.example.sdk.document.DocumentListener
import com.example.sdk.face.FaceDetectionActivity
import com.example.sdk.face.FaceListener

object SdkRouter {

    fun startFaceDetection(context: Context, faceListener: FaceListener) {
        FaceDetectionActivity.faceListener = faceListener
        CameraPreviewActivity.cameraRequestListener = faceListener
        context.startActivity(Intent(context, FaceDetectionActivity::class.java))
    }

    fun startDocumentDetection(context: Context, documentListener: DocumentListener) {
        DocumentDetectionActivity.documentListener = documentListener
        CameraPreviewActivity.cameraRequestListener = documentListener
        context.startActivity(Intent(context, DocumentDetectionActivity::class.java))
    }

}