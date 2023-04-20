package com.example.sdk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.android.gms.internal.mlkit_vision_face.zzmp
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.text.Text

fun mockCameraProvider(): ProcessCameraProvider {
    val constructor = ProcessCameraProvider::class.java.getDeclaredConstructor()
    constructor.isAccessible = true
    val provider = constructor.newInstance()
    constructor.isAccessible = false
    return provider
}

fun mockFace() = Face(
    zzmp(
        1,
        mockRect(), 3F, 4F, 4F, 5F, 6F, 7F, 8F,
        emptyList(),
        emptyList()
    ), null
)

private fun mockRect() = Rect(0, 0, 1000, 1000)

fun mockEmptyText() = Text("text", emptyList())

fun mockValidText() = Text(
    "text", listOf(
        Text.TextBlock(
            "block", mockRect(), emptyList(), "p3", null, listOf(
                Text.Line(
                    "Line", mockRect(), emptyList(), "p3", null, listOf(
                        Text.Element("element", mockRect(), emptyList(), "p3", null)
                    )
                )
            )
        )
    )
)

fun faceBitmapFromAssets(context: Context): Bitmap {
    return context.assets.let {
        BitmapFactory.decodeStream(it.open("face.jpg"))
    }
}

fun cardBitmapFromAssets(context: Context): Bitmap {
    return context.assets.let {
        BitmapFactory.decodeStream(it.open("card.jpeg"))
    }
}