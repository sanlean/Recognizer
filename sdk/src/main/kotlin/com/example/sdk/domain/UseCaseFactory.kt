package com.example.sdk.domain

object UseCaseFactory {
    fun getCameraBindUseCase() = CameraBindUseCase()
    fun getCameraProviderUseCase() = CameraProviderUseCase()
    fun getProcessFaceUseCase() = ProcessFaceUseCase()
    fun getProcessTextUseCase() = ProcessTextUseCase()
    fun getTakePictureUseCase() = TakePictureUseCase()
}