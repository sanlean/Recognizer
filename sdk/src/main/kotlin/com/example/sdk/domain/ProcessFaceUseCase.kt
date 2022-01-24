package com.example.sdk.domain

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetector
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Converted FaceDetector successListener to a coroutine
 */
class ProcessFaceUseCase {

    suspend operator fun invoke(faceDetector: FaceDetector, inputImage: InputImage): MutableList<Face> =
        suspendCoroutine { continuation ->
            faceDetector.process(inputImage).addOnSuccessListener { faces ->
                continuation.resume(faces)
            }.addOnFailureListener { error ->
                continuation.resumeWithException(error)
            }
        }
}