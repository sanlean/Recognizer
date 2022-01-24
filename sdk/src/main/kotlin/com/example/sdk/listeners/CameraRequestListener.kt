package com.example.sdk.listeners

import com.example.sdk.enum.ErrorType

interface CameraRequestListener {
    fun cameraPermissionDenied()
    fun errorProcessingImage(errorType: ErrorType)
}