package com.example.sdk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.Rect
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.android.gms.internal.mlkit_vision_face.zzow
import com.google.android.gms.internal.mlkit_vision_face.zzpc
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.text.Text
import kotlin.coroutines.CoroutineContext

fun mockCameraProvider(): ProcessCameraProvider {
    val constructor = ProcessCameraProvider::class.java.getDeclaredConstructor()
    constructor.isAccessible = true
    val provider = constructor.newInstance()
    constructor.isAccessible = false
    return provider
}

fun mockFace() = Face(
    zzow(
        1,
        mockRect(), 3F, 4F, 4F, 5F, 6F, 7F, 8F,
        emptyList<zzpc>(),
        emptyList<zzpc>()
    ), null
)

private fun mockRect() = Rect(0, 0, 1000, 1000)

fun mockEmptyText() = Text("text", emptyList<Text.TextBlock>())

fun mockTextWithElements(vararg text: String) = Text(
    "text", listOf(
        Text.TextBlock(
            "block", mockRect(), emptyList<Point>(), "p3", null, listOf<Text.Line>(
                Text.Line(
                    "Line", mockRect(), emptyList<Point>(), "p3", null,
                        text.map {
                            Text.Element(it, mockRect(), emptyList<Point>(), "p3", null, 3f, 3f, emptyList<Text.Symbol>())
                        }
                    , 3f, 3f
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