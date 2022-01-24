package com.example.sdk.domain

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TakePictureUseCase {

    suspend operator fun invoke(imageCapture: ImageCapture, executor: Executor): InputImage =
        suspendCoroutine { continuation ->
            imageCapture.takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {

                @ExperimentalGetImage
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    imageProxy.image?.also { image ->
                        continuation.resume(
                            InputImage.fromMediaImage(
                                image,
                                imageProxy.imageInfo.rotationDegrees
                            )
                        )
                    } ?: continuation.resumeWithException(getImageCaptureException())
                }

                override fun onError(exception: ImageCaptureException) {
                    continuation.resumeWithException(exception)
                }

                fun getImageCaptureException() = ImageCaptureException(
                    ImageCapture.ERROR_UNKNOWN,
                    "ImageCaptureError",
                    Throwable()
                )
            })
        }
}