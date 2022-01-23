package com.example.sdk.extensions

import android.graphics.Bitmap
import android.graphics.Rect

fun Bitmap.cropBitmap(frame: Rect): Bitmap{
    val x0 = frame.left
    val y0 = frame.top
    val width = frame.right - frame.left
    val height = frame.bottom - frame.top
    return Bitmap.createBitmap(this, x0, y0, width, height)
}