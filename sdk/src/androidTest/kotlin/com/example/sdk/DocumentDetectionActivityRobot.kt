package com.example.sdk

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.sdk.domain.*
import com.example.sdk.enum.ErrorType
import com.example.sdk.listeners.DocumentListener
import com.example.sdk.presentation.camera.CameraPreviewActivity
import com.example.sdk.presentation.customviews.LoadingButton
import com.example.sdk.presentation.document.DocumentDetectionActivity
import com.google.common.truth.Truth.assertThat
import com.google.mlkit.vision.common.InputImage
import io.mockk.*

internal object DocumentDetectionActivityRobot {
    private val processTextUseCase = spyk<ProcessTextUseCase>()
    private val cameraProviderUseCase = spyk<CameraProviderUseCase>()
    private val cameraBindUseCase = spyk<CameraBindUseCase>()
    private val takePictureUseCase = spyk<TakePictureUseCase>()
    private val listener = spyk<DocumentListener>()

    fun mockUseCaseFactory() {
        mockkObject(UseCaseFactory)
        every { UseCaseFactory.getProcessTextUseCase() } returns processTextUseCase
        every { UseCaseFactory.getCameraProviderUseCase() } returns cameraProviderUseCase
        every { UseCaseFactory.getCameraBindUseCase() } returns cameraBindUseCase
        every { UseCaseFactory.getTakePictureUseCase() } returns takePictureUseCase
    }

    fun mockDocumentListener() {
        every { listener.textFoundOnDocument(any()) } just Runs
        every { listener.noTextFound() } just Runs
        every { listener.errorProcessingImage(any()) } just Runs
        every { listener.cameraPermissionDenied() } just Runs
        DocumentDetectionActivity.documentListener = listener
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

    fun mockSuccessTakingPicture(activity: DocumentDetectionActivity) {
        val inputImage = InputImage.fromBitmap(cardBitmapFromAssets(activity), 90)
        coEvery { takePictureUseCase(any(), any()) } returns inputImage
    }

    fun mockErrorTakingPicture() {
        coEvery { takePictureUseCase(any(), any()) } throws Exception()
    }

    fun mockProcessingValidText() {
        coEvery { processTextUseCase(any(), any()) } returns mockValidText()
    }

    fun mockProcessingNoTextFound() {
        coEvery { processTextUseCase(any(), any()) } returns mockEmptyText()
    }

    fun mockErrorProcessingText() {
        coEvery { processTextUseCase(any(), any()) } throws Exception()
    }

    fun clickTakePicture(activity: DocumentDetectionActivity) {
        activity.findViewById<Button>(R.id.btnLoading).performClick()
    }

    fun clickBack(activity: DocumentDetectionActivity) {
        activity.findViewById<ImageView>(R.id.btnBack).performClick()
    }

    fun assertPictureWasTaken() {
        verify(exactly = 1) { listener.textFoundOnDocument(any()) }
        confirmVerified(listener)
    }

    fun assertErrorTakingPicture() {
        verify(exactly = 1) { listener.errorProcessingImage(ErrorType.CAPTURE_PICTURE_ERROR) }
        confirmVerified(listener)
    }

    fun assertErrorProcessingText() {
        verify(exactly = 1) { listener.errorProcessingImage(ErrorType.PROCESSING_IMAGE_ERROR) }
        confirmVerified(listener)
    }

    fun assertTextFound() {
        verify(exactly = 1) { listener.textFoundOnDocument(any()) }
        confirmVerified(listener)
    }

    fun assertNoTextFound() {
        verify(exactly = 1) { listener.noTextFound() }
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

    fun assertButtonBackContentDescriptionIsCorrect(activity: DocumentDetectionActivity) {
        assertThat(activity.findViewById<ImageView>(R.id.btnBack)).isNotNull()
        assertThat(activity.findViewById<ImageView>(R.id.btnBack).contentDescription).isEqualTo("Back")
    }

    fun assertTextViewDescriptionIsCorrect(activity: DocumentDetectionActivity) {
        assertThat(activity.findViewById<TextView>(R.id.tvPictureDescription)).isNotNull()
        assertThat(activity.findViewById<TextView>(R.id.tvPictureDescription).text).isEqualTo("Place your ID card in the space above")
    }

    fun assertButtonTextIsCorrect(activity: DocumentDetectionActivity) {
        assertThat(activity.findViewById<LoadingButton>(R.id.btnTakeDocumentPicture)).isNotNull()
        assertThat(activity.findViewById<LoadingButton>(R.id.btnTakeDocumentPicture).text).isEqualTo("Take picture")
    }

    fun assertActivityIsFinished(activity: DocumentDetectionActivity) {
        assertThat(activity.isFinishing).isTrue()
    }
}
