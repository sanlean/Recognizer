package com.example.sdk.presentation.face

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import com.example.sdk.domain.CameraBindUseCase
import com.example.sdk.domain.CameraProviderUseCase
import com.example.sdk.domain.ProcessFaceUseCase
import com.example.sdk.domain.TakePictureUseCase
import com.example.sdk.enum.ErrorProcessingFace
import com.example.sdk.enum.FacePrecessed
import com.example.sdk.enum.NoFace
import com.example.sdk.faceBitmapFromAssets
import com.example.sdk.mockFace
import com.google.common.truth.Truth.assertThat
import com.google.mlkit.common.MlKit
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import io.mockk.coEvery
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.R])
class FaceViewModelTest {
    private lateinit var context: Context
    private val processFaceUseCase = spyk<ProcessFaceUseCase>()
    private val cameraProviderUseCase = spyk<CameraProviderUseCase>()
    private val cameraBindUseCase = spyk<CameraBindUseCase>()
    private val takePictureUseCase = spyk<TakePictureUseCase>()
    private lateinit var viewModel: FaceViewModel
    private lateinit var faceBitmap: Bitmap
    private lateinit var cardBitmap: Bitmap

    @Before
    fun setup() {
        viewModel = FaceViewModel(
            processFaceUseCase,
            cameraProviderUseCase,
            cameraBindUseCase,
            takePictureUseCase
        )
        context = RuntimeEnvironment.application
        faceBitmap = faceBitmapFromAssets(context, "face.jpg")
        cardBitmap = faceBitmapFromAssets(context, "card.jpeg")
        try {
            MlKit.initialize(context)
        } catch (ignore: Exception) {
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `when passing a face picture bitmap be sure it will be correctly processed`() {
        coEvery { processFaceUseCase(any(),any()) } returns mutableListOf(mockFace())
        runBlockingTest {
            val inputImage = InputImage.fromBitmap(faceBitmap, 90)
            viewModel.processFace(inputImage)
            assertThat(viewModel.faceLiveData.value).isInstanceOf(FacePrecessed::class.java)
            val processedFaceBitmap = (viewModel.faceLiveData.value as FacePrecessed).bitmap
            //If has ouput bitmap, picture was processed and face detected successfully
            assertThat(processedFaceBitmap).isNotNull()
        }
    }

    @Test
    fun `when passing a text picture bitmap be sure no face will be found`() {
        coEvery { processFaceUseCase(any(),any()) } returns mutableListOf<Face>()
        val inputImage = InputImage.fromBitmap(cardBitmap, 90)
        runBlockingTest {
            viewModel.processFace(inputImage)
            //If is NoFace state so the process worked successfully and no face was found
            assertThat(viewModel.faceLiveData.value).isSameInstanceAs(NoFace)
        }
    }

    @Test
    fun `when throwing an error this error should be set to livedata`() {
        val inputImage = InputImage.fromBitmap(cardBitmap, 90)
        val error = Exception()
        coEvery { processFaceUseCase(any(), any()) } throws error
        runBlockingTest {
            viewModel.processFace(inputImage)
            //If the face processing throws an error the livedata should be updated with that error
            assertThat(viewModel.faceLiveData.value).isInstanceOf(ErrorProcessingFace::class.java)
            assertThat((viewModel.faceLiveData.value as ErrorProcessingFace).error).isSameInstanceAs(
                error
            )
        }

    }

}