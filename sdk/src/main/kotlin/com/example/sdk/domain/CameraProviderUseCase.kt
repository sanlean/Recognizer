package com.example.sdk.domain

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Returns a suspend ProcessCameraProvider, due to CameraX is not natively
 * compatible with coroutines.
 * Attaches the instance of ProcessCameraProvider provided by the callback
 * ListenableFuture to a coroutines Continuation using continuation.resume()
 */
class CameraProviderUseCase {

    suspend operator fun invoke(context: Context, executor: Executor): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(context).apply {
                addListener({
                    val processCameraProvider = get()
                    continuation.resume(processCameraProvider)
                }, executor)
            }
        }
}