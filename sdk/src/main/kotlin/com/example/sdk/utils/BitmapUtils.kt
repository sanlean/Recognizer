package com.example.sdk.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import com.example.sdk.FrameParameters

fun Bitmap.cropBitmap(frame: FrameParameters): Bitmap{
    val matrix = Matrix().apply {
        postRotate(90F)
    }
    Bitmap.createBitmap(this, 0, 0, width, height, matrix, true).let {
        val x0 = frame.horizontalMarginPercentage * it.width
        val y0 = frame.topMarginPercentage * it.height
        val width = it.width - (x0 * 2)
        val height = (frame.width * frame.aspectRatio) + y0
        return Bitmap.createBitmap(it, x0.toInt(), y0.toInt(), width.toInt(), height.toInt())
    }
}

fun Bitmap.cropBitmap(frame: Rect): Bitmap{
    val x0 = frame.left
    val y0 = frame.top
    val width = frame.right - frame.left
    val height = frame.bottom - frame.top
    return Bitmap.createBitmap(this, x0, y0, width, height)
}

fun Bitmap.rotate(degrees: Float): Bitmap{
    val matrix = Matrix().apply {
        postRotate(degrees)
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}