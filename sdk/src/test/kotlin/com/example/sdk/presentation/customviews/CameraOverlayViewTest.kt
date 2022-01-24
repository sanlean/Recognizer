package com.example.sdk.presentation.customviews

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import com.example.sdk.presentation.camera.CameraPreviewActivity
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.R])
class CameraOverlayViewTest {
    private lateinit var scenario: ActivityScenario<CameraPreviewActivity>
    private val canvas = mockk<Canvas>()
    private val path = mockk<Path>()
    private val paint = mockk<Paint>()
    private val frame = mockk<RectF>()

    @Before
    fun setup(){
        scenario = launchActivity()
    }

    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun `test if getFrameFecF() has expected value when using java reflection to test private function`(){
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            val cameraView = CameraOverlayView(activity)
            val frame = CameraOverlayView::class.java.getDeclaredMethod("getFrameRecF").let {
                it.isAccessible = true
                it.invoke(cameraView) as RectF
            }
            val x0 = cameraView.horizontalMarginPercent * cameraView.width
            val y0 = cameraView.width - (x0)
            val x1 = cameraView.topMarginPercent * cameraView.height
            val y1 = y0 + ((x1 - x0) / cameraView.aspectRatio)
            assertThat(frame.left).isEqualTo(x0)
            assertThat(frame.top).isEqualTo(y0)
            assertThat(frame.right).isEqualTo(x1)
            assertThat(frame.bottom).isEqualTo(y1)
        }
    }

    @Test
    fun `test if drawFaceBackground has is working properly when using java reflection to test private function`(){
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            val cameraView = CameraOverlayView(activity)
            CameraOverlayView::class.java.getDeclaredMethod("drawFaceBackground", Canvas::class.java, Path::class.java, Paint::class.java, RectF::class.java).let {
                it.isAccessible = true
                every { path.addOval(any(), any()) } just Runs
                every { canvas.drawPath(any(), any()) } just Runs
                it.invoke(cameraView, canvas, path, paint, frame)
                verify { path.addOval(frame, Path.Direction.CW) }
                verify { canvas.drawPath(path, paint) }
                confirmVerified(path)
                confirmVerified(canvas)
            }
        }
    }

    @Test
    fun `test if drawFaceStroke has is working properly when using java reflection to test private function`(){
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            val cameraView = CameraOverlayView(activity)
            CameraOverlayView::class.java.getDeclaredMethod("drawFaceStroke", Canvas::class.java, RectF::class.java, Paint::class.java).let {
                it.isAccessible = true
                every { canvas.drawOval(any(), any()) } just Runs
                it.invoke(cameraView, canvas, frame, paint)
                verify { canvas.drawOval(frame, paint) }
                confirmVerified(canvas)
            }
        }
    }

    @Test
    fun `test if drawDocumentBackground has is working properly when using java reflection to test private function`(){
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            val cameraView = CameraOverlayView(activity)
            CameraOverlayView::class.java.getDeclaredMethod("drawDocumentBackground", Canvas::class.java, Path::class.java, Paint::class.java, RectF::class.java).let {
                it.isAccessible = true
                every { path.addRoundRect(any(), any(), any(), any()) } just Runs
                every { canvas.drawPath(any(), any()) } just Runs
                it.invoke(cameraView, canvas, path, paint, frame)
                verify { path.addRoundRect(frame, 60f, 60f, Path.Direction.CW) }
                verify { canvas.drawPath(path, paint) }
                confirmVerified(path)
                confirmVerified(canvas)
            }
        }
    }

    @Test
    fun `test if drawDocumentStroke has is working properly when using java reflection to test private function`(){
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            val cameraView = CameraOverlayView(activity)
            CameraOverlayView::class.java.getDeclaredMethod("drawDocumentStroke", Canvas::class.java, RectF::class.java, Paint::class.java).let {
                it.isAccessible = true
                every { canvas.drawRoundRect(any(), any(), any(), any()) } just Runs
                it.invoke(cameraView, canvas, frame, paint)
                verify { canvas.drawRoundRect(frame, 55f, 55f, paint) }
                confirmVerified(canvas)
            }
        }
    }
}