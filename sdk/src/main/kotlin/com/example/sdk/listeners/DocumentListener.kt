package com.example.sdk.listeners

import com.example.sdk.listeners.CameraRequestListener

interface DocumentListener: CameraRequestListener {
    fun textFoundOnDocument(text: String)
    fun noTextFound()
}