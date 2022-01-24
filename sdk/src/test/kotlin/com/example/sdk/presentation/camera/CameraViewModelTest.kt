package com.example.sdk.presentation.camera

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import com.example.sdk.domain.CameraBindUseCase
import com.example.sdk.domain.CameraProviderUseCase
import com.example.sdk.domain.TakePictureUseCase
import com.example.sdk.enum.CameraLoaded
import com.example.sdk.enum.ErrorLoadingCamera
import com.example.sdk.enum.ErrorTakingPicture
import com.example.sdk.enum.PictureTaken
import com.example.sdk.mockCameraProvider
import com.google.common.truth.Truth.assertThat
import com.google.mlkit.vision.common.InputImage
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.R])
@ExperimentalCoroutinesApi
class CameraViewModelTest {
    private val cameraProviderUseCase = spyk<CameraProviderUseCase>()
    private val cameraBindUseCase = spyk<CameraBindUseCase>()
    private val takePictureUseCase = spyk<TakePictureUseCase>()
    private val provider = mockCameraProvider()
    private lateinit var viewModel: CameraViewModel

    @Before
    fun setup(){
        viewModel = CameraViewModel(
            cameraProviderUseCase,
            cameraBindUseCase,
            takePictureUseCase
        )
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun `test successfully load camera`(){
        coEvery { cameraProviderUseCase(any(), any()) } returns provider
        every { cameraBindUseCase(any(), any(), any(), any(), any()) } just Runs
        runBlockingTest {
            val activity = AppCompatActivity()
            viewModel.startCamera(
                activity,
                activity,
                CameraSelector.DEFAULT_BACK_CAMERA,
                Preview.Builder().build()
            )
        }
        assertThat(viewModel.liveData.value).isEqualTo(CameraLoaded)
    }

    @Test
    fun `test error when loading camera`(){
        coEvery { cameraProviderUseCase.invoke(any(), any()) } returns provider
        every { cameraBindUseCase.invoke(any(), any(), any(), any(), any()) } throws Exception()
        runBlockingTest {
            val activity = AppCompatActivity()
            viewModel.startCamera(
                activity,
                activity,
                CameraSelector.DEFAULT_BACK_CAMERA,
                Preview.Builder().build()
            )
        }
        assertThat(viewModel.liveData.value).isInstanceOf(ErrorLoadingCamera::class.java)
    }

    @Test
    fun `test successfully take a picture`(){
        val inputImage: InputImage = mockk()
        coEvery { takePictureUseCase(any(), any()) } returns inputImage
        runBlockingTest {
            viewModel.takePicture()
        }
        assertThat(viewModel.liveData.value).isInstanceOf(PictureTaken::class.java)
        assertThat((viewModel.liveData.value as PictureTaken).image).isSameInstanceAs(inputImage)
    }

    @Test
    fun `test error when taking a picture`(){
        coEvery { takePictureUseCase(any(), any()) } throws Exception()
        runBlockingTest {
            viewModel.takePicture()
            assertThat(viewModel.liveData.value).isInstanceOf(ErrorTakingPicture::class.java)
        }
    }

}