package com.example.sdk.extensions

import android.graphics.Bitmap
import android.graphics.Rect
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.lang.RuntimeException

@RunWith(RobolectricTestRunner::class)
class BitmapExtensionsTest {

    @Test
    fun `cropBitmap returns correct cropped bitmap`() {
        val original = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
        original.eraseColor(0xFFFF0000.toInt())

        val frame = Rect(2, 2, 7, 7)

        val cropped = original.cropBitmap(frame)

        assertThat(cropped.width).isEqualTo(5)
        assertThat(cropped.height).isEqualTo(5)

        assertThat(cropped.getPixel(2, 2)).isEqualTo(original.getPixel(4, 4))
    }

    @Test
    fun `cropBitmap with full size returns identical bitmap in size`() {
        val original = Bitmap.createBitmap(8, 6, Bitmap.Config.ARGB_8888)
        val frame = Rect(0, 0, 8, 6)

        val cropped = original.cropBitmap(frame)

        assertThat(cropped.width).isEqualTo(8)
        assertThat(cropped.height).isEqualTo(6)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `cropBitmap throws when frame is out of bounds`() {
        val original = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888)
        val frame = Rect(3, 3, 10, 10)
        original.cropBitmap(frame)
    }

    @Test
    fun `cropBitmap with zero width or height throws`() {
        val original = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888)
        val frameZeroWidth = Rect(2, 2, 2, 4)
        val frameZeroHeight = Rect(1, 3, 4, 3)

        assertThrows(RuntimeException::class.java) {
            original.cropBitmap(frameZeroWidth)
        }
        assertThrows(RuntimeException::class.java) {
            original.cropBitmap(frameZeroHeight)
        }
    }
}