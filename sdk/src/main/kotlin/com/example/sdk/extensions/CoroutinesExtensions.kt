package com.example.sdk.extensions

import android.content.Context
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognizer
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Returns a suspend ProcessCameraProvider, due to CameraX is not natively
 * compatible with coroutines.
 * Attaches the instance of ProcessCameraProvider provided by the callback
 * ListenableFuture to a coroutines Continuation using continuation.resume()
 */
suspend fun Context.getCameraProvider(executor: Executor): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).apply {
            addListener({
                val processCameraProvider = get()
                continuation.resume(processCameraProvider)
            }, executor)
        }
    }

/**
 *
 */
suspend fun ImageCapture.takePicture(executor: Executor): InputImage {
    return suspendCoroutine { continuation ->

        takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {

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

/**
 *
 */
suspend fun FaceDetector.processFace(image: InputImage): MutableList<Face>{
    return suspendCoroutine { continuation ->
        process(image).addOnSuccessListener { faces ->
            continuation.resume(faces)
        }.addOnFailureListener { error ->
            continuation.resumeWithException(error)
        }
    }
}

/**
 *
 */
suspend fun TextRecognizer.processText(image: InputImage): Text{
    return suspendCoroutine { continuation ->
        process(image).addOnSuccessListener { text ->
            continuation.resume(text)
        }.addOnFailureListener { error ->
            continuation.resumeWithException(error)
        }
    }
}