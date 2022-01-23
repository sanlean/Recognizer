package com.example.sdk.camera

import com.example.sdk.enum.ErrorType

interface CameraRequestListener {
    fun cameraPermissionDenied()
    fun errorProcessingImage(errorType: ErrorType)
}