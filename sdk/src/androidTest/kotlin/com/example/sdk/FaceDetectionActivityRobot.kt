package com.example.sdk

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.sdk.domain.*
import com.example.sdk.enum.ErrorType
import com.example.sdk.listeners.FaceListener
import com.example.sdk.presentation.camera.CameraPreviewActivity
import com.example.sdk.presentation.customviews.LoadingButton
import com.example.sdk.presentation.face.FaceDetectionActivity
import com.google.common.truth.Truth.assertThat
import com.google.mlkit.vision.common.InputImage
import io.mockk.*

internal object FaceDetectionActivityRobot {
    private val processFaceUseCase = spyk<ProcessFaceUseCase>()
    private val cameraProviderUseCase = spyk<CameraProviderUseCase>()
    private val cameraBindUseCase = spyk<CameraBindUseCase>()
    private val takePictureUseCase = spyk<TakePictureUseCase>()
    private val listener = spyk<FaceListener>()

    fun mockUseCaseFactory() {
        mockkObject(UseCaseFactory)
        every { UseCaseFactory.getProcessFaceUseCase() } returns processFaceUseCase
        every { UseCaseFactory.getCameraProviderUseCase() } returns cameraProviderUseCase
        every { UseCaseFactory.getCameraBindUseCase() } returns cameraBindUseCase
        every { UseCaseFactory.getTakePictureUseCase() } returns takePictureUseCase
    }

    fun mockFaceListener() {
        every { listener.facePictureFound(any()) } just Runs
        every { listener.moreThanOneFaceFound() } just Runs
        every { listener.noFaceFound() } just Runs
        every { listener.errorProcessingImage(any()) } just Runs
        every { listener.cameraPermissionDenied() } just Runs
        FaceDetectionActivity.faceListener = listener
        CameraPreviewActivity.cameraRequestListener = listener
    }

    fun mockSuccessLoadingCamera() {
        every { cameraBindUseCase(any(), any(), any(), any(), any()) } just Runs
        coEvery { cameraProviderUseCase(any(), any()) } returns mockCameraProvider()
    }

    fun mockErrorLoadingCamera() {
        every { cameraBindUseCase(any(), any(), any(), any(), any()) } just Runs
        coEvery { cameraProviderUseCase(any(), any()) } throws Exception()
    }

    fun mockSuccessTakingPicture(activity: FaceDetectionActivity) {
        val inputImage = InputImage.fromBitmap(faceBitmapFromAssets(activity), 90)
        coEvery { takePictureUseCase(any(), any()) } returns inputImage
    }

    fun mockErrorTakingPicture() {
        coEvery { takePictureUseCase(any(), any()) } throws Exception()
    }

    fun mockProcessingManyFaces() {
        coEvery { processFaceUseCase(any(), any()) } returns mutableListOf(mockFace(), mockFace())
    }

    fun mockProcessingOneFace() {
        coEvery { processFaceUseCase(any(), any()) } returns mutableListOf(mockFace())
    }

    fun mockProcessingNoFaces() {
        coEvery { processFaceUseCase(any(), any()) } returns mutableListOf()
    }

    fun mockErrorProcessingFaces() {
        coEvery { processFaceUseCase(any(), any()) } throws Exception()
    }

    fun clickTakePicture(activity: FaceDetectionActivity) {
        activity.findViewById<Button>(R.id.btnLoading).performClick()
    }

    fun clickBack(activity: FaceDetectionActivity) {
        activity.findViewById<ImageView>(R.id.btnBack).performClick()
    }

    fun assertPictureWasTaken() {
        verify(exactly = 1) { listener.facePictureFound(any()) }
        confirmVerified(listener)
    }

    fun assertErrorTakingPicture() {
        verify(exactly = 1) { listener.errorProcessingImage(ErrorType.CAPTURE_PICTURE_ERROR) }
        confirmVerified(listener)
    }

    fun assertErrorProcessingFaces() {
        verify(exactly = 1) { listener.errorProcessingImage(ErrorType.PROCESSING_IMAGE_ERROR) }
        confirmVerified(listener)
    }

    fun assertOnlyOneFaceFound() {
        verify(exactly = 1) { listener.facePictureFound(any()) }
        confirmVerified(listener)
    }

    fun assertMoreThaOneFaceFound() {
        verify(exactly = 1) { listener.moreThanOneFaceFound() }
        confirmVerified(listener)
    }

    fun assertNoFaceFound() {
        verify(exactly = 1) { listener.noFaceFound() }
        confirmVerified(listener)
    }

    fun assertCameraNotLoaded() {
        verify(exactly = 1) { listener.errorProcessingImage(ErrorType.CAMERA_LOADING_ERROR) }
        confirmVerified(listener)
    }

    fun assertCameraWasLoaded() {
        verify(exactly = 1) { cameraBindUseCase(any(), any(), any(), any(), any()) }
        coVerify(exactly = 1) { cameraProviderUseCase(any(), any()) }
    }

    fun assertButtonBackContentDescriptionIsCorrect(activity: FaceDetectionActivity) {
        assertThat(activity.findViewById<ImageView>(R.id.btnBack)).isNotNull()
        assertThat(activity.findViewById<ImageView>(R.id.btnBack).contentDescription).isEqualTo("Back")
    }

    fun assertTextViewDescriptionIsCorrect(activity: FaceDetectionActivity) {
        assertThat(activity.findViewById<TextView>(R.id.tvPictureDescription)).isNotNull()
        assertThat(activity.findViewById<TextView>(R.id.tvPictureDescription).text).isEqualTo("Place your face in the space above")
    }

    fun assertButtonTextIsCorrect(activity: FaceDetectionActivity) {
        assertThat(activity.findViewById<LoadingButton>(R.id.btnTakeFacePicture)).isNotNull()
        assertThat(activity.findViewById<LoadingButton>(R.id.btnTakeFacePicture).text).isEqualTo("Take picture")
    }

    fun assertActivityIsFinished(activity: FaceDetectionActivity) {
        assertThat(activity.isFinishing).isTrue()
    }
}
