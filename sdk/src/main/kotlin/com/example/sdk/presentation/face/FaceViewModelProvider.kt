package com.example.sdk.presentation.face

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sdk.domain.CameraBindUseCase
import com.example.sdk.domain.CameraProviderUseCase
import com.example.sdk.domain.ProcessFaceUseCase
import com.example.sdk.domain.TakePictureUseCase

class FaceViewModelProvider: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ProcessFaceUseCase::class.java,
            CameraProviderUseCase::class.java,
            CameraBindUseCase::class.java,
            TakePictureUseCase::class.java
        ).newInstance(ProcessFaceUseCase(), CameraProviderUseCase(), CameraBindUseCase(), TakePictureUseCase())
    }
}