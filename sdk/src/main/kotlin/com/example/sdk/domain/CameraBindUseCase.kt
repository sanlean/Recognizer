package com.example.sdk.domain

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner

class CameraBindUseCase {

    operator fun invoke(
        lifecycleOwner: LifecycleOwner,
        cameraProvider: ProcessCameraProvider,
        cameraSelector: CameraSelector,
        cameraPreview: Preview,
        imageCapture: ImageCapture
    ){
        // Unbind use cases before rebinding
        cameraProvider.unbindAll()

        // Bind use cases to camera
        cameraProvider.bindToLifecycle(
            lifecycleOwner, cameraSelector, cameraPreview, imageCapture
        )
    }
}