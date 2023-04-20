package com.example.sdk.presentation.face

import android.os.Build
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.example.sdk.R
import com.example.sdk.domain.CameraBindUseCase
import com.example.sdk.domain.UseCaseFactory
import com.example.sdk.enum.ErrorType.CAMERA_LOADING_ERROR
import com.example.sdk.listeners.FaceListener
import com.example.sdk.presentation.camera.CameraPreviewActivity.Companion.cameraRequestListener
import com.example.sdk.presentation.customviews.LoadingButton
import com.example.sdk.presentation.face.FaceDetectionActivity.Companion.faceListener
import com.google.common.truth.Truth.assertThat
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.R], instrumentedPackages = ["androidx.loader.content"])
class FaceDetectionActivityTest {
    private lateinit var scenario: ActivityScenario<FaceDetectionActivity>

    @Rule
    @JvmField
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.CAMERA)

    @Before
    fun setup() {
        val useCase = mockk<CameraBindUseCase>()
        mockkObject(UseCaseFactory)
        every { useCase.invoke(any(),any(),any(),any(),any()) } just Runs
        every { UseCaseFactory.getCameraBindUseCase() } returns useCase
        scenario = launchActivity()
    }

    @After
    fun tearDown(){
        scenario.close()
        unmockkAll()
    }

    @Test
    fun `verify if texts are displayed correctly`() {
        scenario.onActivity { activity ->
            assertThat(activity.findViewById<TextView>(R.id.tvPictureDescription).text).isEqualTo("Place your face in the space above")
            assertThat(activity.findViewById<LoadingButton>(R.id.btnTakeFacePicture).text).isEqualTo(
                "Take picture"
            )
        }

    }

    @Test
    fun `when updating error message should call listener and finish activity`() {
        scenario.onActivity { activity ->
            val listener = mockk<FaceListener>()
            cameraRequestListener = listener
            faceListener = listener
            every { listener.errorProcessingImage(CAMERA_LOADING_ERROR) } just Runs
            activity.updateError(Exception(), CAMERA_LOADING_ERROR)
            verify(exactly = 1) { listener.errorProcessingImage(CAMERA_LOADING_ERROR) }
            confirmVerified(listener)
            assertThat(activity.isFinishing).isTrue()
        }
    }
}