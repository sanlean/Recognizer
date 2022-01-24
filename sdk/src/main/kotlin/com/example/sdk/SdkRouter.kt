package com.example.sdk

import android.content.Context
import android.content.Intent
import com.example.sdk.presentation.camera.CameraPreviewActivity
import com.example.sdk.presentation.document.DocumentDetectionActivity
import com.example.sdk.listeners.DocumentListener
import com.example.sdk.presentation.face.FaceDetectionActivity
import com.example.sdk.listeners.FaceListener

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