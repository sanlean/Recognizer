package com.example.sdk.domain

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognizer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Convert TextRecognizer success listener to a couroutine
 */
class ProcessTextUseCase {

    suspend operator fun invoke(textRecognizer: TextRecognizer, image: InputImage): Text =
        suspendCoroutine { continuation ->
            textRecognizer.process(image).addOnSuccessListener { text ->
                continuation.resume(text)
            }.addOnFailureListener { error ->
                continuation.resumeWithException(error)
            }
        }
}