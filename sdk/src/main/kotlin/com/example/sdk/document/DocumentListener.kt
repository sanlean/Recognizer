package com.example.sdk.document

import com.example.sdk.camera.CameraRequestListener

interface DocumentListener: CameraRequestListener {
    fun textFoundOnDocument(text: String)
    fun noTextFound()
}