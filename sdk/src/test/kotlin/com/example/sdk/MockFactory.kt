package com.example.sdk

import androidx.camera.lifecycle.ProcessCameraProvider
import org.junit.Test

import org.junit.Assert.*

fun mockCameraProvider(): ProcessCameraProvider {
    val constructor = ProcessCameraProvider::class.java.getDeclaredConstructor()
    constructor.isAccessible = true
    val provider = constructor.newInstance()
    constructor.isAccessible = false
    return provider
}