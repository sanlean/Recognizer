package com.example.sdk.document

import com.example.sdk.CameraRequestListener

interface DocumentListener: CameraRequestListener {
    fun textFoundOnDocument(text: String)
    fun noTextFound()
}