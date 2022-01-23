package com.example.sdk.listeners

import android.graphics.Bitmap

interface FaceListener: CameraRequestListener {
    fun facePictureFound(facePicture: Bitmap)
    fun noFaceFound()
    fun moreThanOneFaceFound()
}