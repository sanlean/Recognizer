package com.example.sdk

interface CameraRequestListener {
    fun cameraPermissionDenied()
    fun errorProcessingImage(errorType: ErrorType)
}