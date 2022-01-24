package com.example.sdk.presentation.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sdk.domain.CameraBindUseCase
import com.example.sdk.domain.CameraProviderUseCase
import com.example.sdk.domain.ProcessTextUseCase
import com.example.sdk.domain.TakePictureUseCase

class DocumentViewModelProvider: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ProcessTextUseCase::class.java,
            CameraProviderUseCase::class.java,
            CameraBindUseCase::class.java,
            TakePictureUseCase::class.java
        ).newInstance(ProcessTextUseCase(), CameraProviderUseCase(), CameraBindUseCase(), TakePictureUseCase())
    }
}