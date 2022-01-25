package com.example.sdk.presentation.face

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sdk.R
import com.example.sdk.enum.ErrorType.CAMERA_LOADING_ERROR
import com.example.sdk.listeners.FaceListener
import com.example.sdk.presentation.camera.CameraPreviewActivity.Companion.cameraRequestListener
import com.example.sdk.presentation.customviews.LoadingButton
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
@Config(sdk = [Build.VERSION_CODES.R],
        instrumentedPackages = [
    // required to access final members on androidx.loader.content.ModernAsyncTask
    "androidx.loader.content"
])
class FaceDetectionActivityTest {
    private lateinit var scenario: ActivityScenario<FaceDetectionActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
    }

    @Test
    fun `verify if texts are displayed correctly`() {
        val controller:ActivityController<FaceDetectionActivity> = buildActivity(FaceDetectionActivity::class.java).setup()
        val activity = controller.resume().get()

            //onView(withId(R.id.btnTakeFacePicture)).check(matches(withText("Place your face in the space below")))
            assertThat(activity.findViewById<LoadingButton>(R.id.btnTakeFacePicture)).isNotNull()
            assertThat(activity.findViewById<LoadingButton>(R.id.btnTakeFacePicture).text).isEqualTo("Place your face in the space below")

    }

    @Test
    fun `when updating error message should call listener and finish activity`(){
        scenario.moveToState(Lifecycle.State.CREATED)
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