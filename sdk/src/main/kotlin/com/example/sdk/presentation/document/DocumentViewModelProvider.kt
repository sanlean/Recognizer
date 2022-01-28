package com.example.sdk.presentation.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sdk.domain.*

class DocumentViewModelProvider : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ProcessTextUseCase::class.java,
            CameraProviderUseCase::class.java,
            CameraBindUseCase::class.java,
            TakePictureUseCase::class.java
        ).newInstance(
            UseCaseFactory.getProcessTextUseCase(),
            UseCaseFactory.getCameraProviderUseCase(),
            UseCaseFactory.getCameraBindUseCase(),
            UseCaseFactory.getTakePictureUseCase()
        )
    }
}