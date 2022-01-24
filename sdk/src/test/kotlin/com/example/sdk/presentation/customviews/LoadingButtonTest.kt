package com.example.sdk.presentation.customviews

import android.os.Build
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import com.example.sdk.presentation.camera.CameraPreviewActivity
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.R])
class LoadingButtonTest {
    private lateinit var scenario: ActivityScenario<CameraPreviewActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
    }

    @Test
    fun `when hideLoading() should hide progress and show button text`(){
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            val loadingButton = LoadingButton(activity)
            loadingButton.text = "Example text"
            loadingButton.hideLoading()
            assertThat(loadingButton.button.text.toString()).isEqualTo("Example text")
            assertThat(loadingButton.loading.isVisible).isFalse()
        }
    }

    @Test
    fun `when showLoading() should show progress and hide button text`(){
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            val loadingButton = LoadingButton(activity)
            loadingButton.text = "Example text"
            loadingButton.showLoading()
            assertThat(loadingButton.button.text.toString()).isEmpty()
            assertThat(loadingButton.loading.isVisible).isTrue()
        }
    }

    @Test
    fun `when click on child Button should execute parent LoadingButton clickListener`(){
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            val loadingButton = LoadingButton(activity)
            val clickListener: () -> Unit = mockk()
            loadingButton.setClickListener(clickListener)
            every { clickListener() } just Runs
            loadingButton.button.performClick()
            verify(exactly = 1) { clickListener() }
            confirmVerified(clickListener)
        }
    }
}