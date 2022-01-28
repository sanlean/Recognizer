package com.example.sdk.presentation.face

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sdk.domain.*

class FaceViewModelProvider : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ProcessFaceUseCase::class.java,
            CameraProviderUseCase::class.java,
            CameraBindUseCase::class.java,
            TakePictureUseCase::class.java
        ).newInstance(
            UseCaseFactory.getProcessFaceUseCase(),
            UseCaseFactory.getCameraProviderUseCase(),
            UseCaseFactory.getCameraBindUseCase(),
            UseCaseFactory.getTakePictureUseCase()
        )
    }
}