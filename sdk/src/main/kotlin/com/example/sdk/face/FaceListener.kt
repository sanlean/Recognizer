package com.example.sdk.face

import android.graphics.Bitmap
import com.example.sdk.CameraRequestListener

interface FaceListener: CameraRequestListener {
    fun facePictureFound(facePicture: Bitmap)
    fun noFaceFound()
    fun moreThanOneFaceFound()
}