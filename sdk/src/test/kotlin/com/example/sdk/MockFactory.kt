package com.example.sdk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.android.gms.internal.mlkit_vision_face.zzmp
import com.google.mlkit.vision.face.Face
import org.junit.Test

import org.junit.Assert.*

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
        Rect(0, 0, 1000, 1000), 3F, 4F, 4F, 5F, 6F, 7F, 8F,
        emptyList(),
        emptyList()
    ), null
)

fun faceBitmapFromAssets(context: Context, name: String): Bitmap {
    return context.assets.let {
        BitmapFactory.decodeStream(it.open(name))
    }
}