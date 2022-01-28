package com.example.sdk.presentation.face

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import com.example.sdk.R
import com.example.sdk.enum.ErrorType.CAMERA_LOADING_ERROR
import com.example.sdk.listeners.DocumentListener
import com.example.sdk.listeners.FaceListener
import com.example.sdk.presentation.camera.CameraPreviewActivity.Companion.cameraRequestListener
import com.example.sdk.presentation.customviews.LoadingButton
import com.example.sdk.presentation.document.DocumentDetectionActivity
import com.example.sdk.presentation.document.DocumentDetectionActivity.Companion.documentListener
import com.example.sdk.presentation.face.FaceDetectionActivity.Companion.faceListener
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.R], instrumentedPackages = ["androidx.loader.content"])
class DocumentDetectionActivityTest {
    private lateinit var scenario: ActivityScenario<DocumentDetectionActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
    }

    @Test
    fun `verify if texts are displayed correctly`() {
        val controller:ActivityController<DocumentDetectionActivity> = buildActivity(DocumentDetectionActivity::class.java).setup()
        val activity = controller.resume().get()
            assertThat(activity.findViewById<LoadingButton>(R.id.btnTakeDocumentPicture)).isNotNull()
            assertThat(activity.findViewById<LoadingButton>(R.id.btnTakeDocumentPicture).text).isEqualTo("Take picture")
    }

    @Test
    fun `when updating error message should call listener and finish activity`(){
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            val listener = mockk<DocumentListener>()
            cameraRequestListener = listener
            documentListener = listener
            every { listener.errorProcessingImage(CAMERA_LOADING_ERROR) } just Runs
            activity.updateError(Exception(), CAMERA_LOADING_ERROR)
            verify(exactly = 1) { listener.errorProcessingImage(CAMERA_LOADING_ERROR) }
            confirmVerified(listener)
            assertThat(activity.isFinishing).isTrue()
        }
    }
}