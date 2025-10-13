package com.example.sdk.extensions

import android.graphics.Point
import com.example.sdk.mockEmptyText
import com.example.sdk.mockTextWithElements
import com.google.common.truth.Truth.assertThat
import com.google.mlkit.vision.text.Text
import org.junit.Test

class TextExtensionsTest {

    @Test
    fun `isTextEmpty returns true when textBlocks is empty`() {
        val text = mockEmptyText()
        assertThat(text.isTextEmpty()).isTrue()
    }

    @Test
    fun `isTextEmpty returns true when all elements are blank`() {
        val text = mockTextWithElements("   ", "", "\n")
        assertThat(text.isTextEmpty()).isTrue()
    }

    @Test
    fun `isTextEmpty returns false when at least one element is not blank`() {
        val text = mockTextWithElements("   ", "Hello", "")
        assertThat(text.isTextEmpty()).isFalse()
    }

    @Test
    fun `isTextEmpty returns true when all elements are blank in multiple blocks and lines`() {
        // Para múltiplos blocks/lines, crie manualmente:
        val block1 = Text.TextBlock(
            "block1", android.graphics.Rect(0,0,1,1), emptyList<Point>(),
            "p3", null, listOf(
                Text.Line(
                    "line1", android.graphics.Rect(0,0,1,1), emptyList<Point>(),
                    "p3", null, listOf(
                        Text.Element("   ", android.graphics.Rect(0,0,1,1), emptyList<Point>(), "p3", null, 1f, 1f, emptyList<Text.Symbol>())
                    ), 1f, 1f
                )
            )
        )
        val block2 = Text.TextBlock(
            "block2", android.graphics.Rect(0,0,1,1), emptyList<Point>(),
            "p3", null, listOf(
                Text.Line(
                    "line2", android.graphics.Rect(0,0,1,1), emptyList<Point>(),
                    "p3", null, listOf(
                        Text.Element("", android.graphics.Rect(0,0,1,1), emptyList<Point>(), "p3", null, 1f, 1f, emptyList<Text.Symbol>())
                    ), 1f, 1f
                )
            )
        )
        val text = Text("text", listOf(block1, block2))
        assertThat(text.isTextEmpty()).isTrue()
    }

    @Test
    fun `isTextEmpty returns false when one element in nested structure is not blank`() {
        val block1 = Text.TextBlock(
            "block1", android.graphics.Rect(0,0,1,1), emptyList<Point>(),
            "p3", null, listOf(
                Text.Line(
                    "line1", android.graphics.Rect(0,0,1,1), emptyList<Point>(),
                    "p3", null, listOf(
                        Text.Element("   ", android.graphics.Rect(0,0,1,1), emptyList<Point>(), "p3", null, 1f, 1f, emptyList<Text.Symbol>())
                    ), 1f, 1f
                )
            )
        )
        val block2 = Text.TextBlock(
            "block2", android.graphics.Rect(0,0,1,1), emptyList<Point>(),
            "p3", null, listOf(
                Text.Line(
                    "line2", android.graphics.Rect(0,0,1,1), emptyList<Point>(),
                    "p3", null, listOf(
                        Text.Element("abc", android.graphics.Rect(0,0,1,1), emptyList<Point>(), "p3", null, 1f, 1f, emptyList<Text.Symbol>())
                    ), 1f, 1f
                )
            )
        )
        val text = Text("text", listOf(block1, block2))
        assertThat(text.isTextEmpty()).isFalse()
    }
}