package com.example.sdk.enum

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage

sealed class StateCamera
object LoadingTakingPicture : StateCamera()
data class PictureTaken(val image: InputImage) : StateCamera()
data class ErrorTakingPicture(val error: Throwable) : StateCamera()
object LoadingCamera : StateCamera()
object CameraLoaded : StateCamera()
data class ErrorLoadingCamera(val error: Throwable) : StateCamera()

sealed class StateFace
object ProcessingFace : StateFace()
data class FacePrecessed(val bitmap: Bitmap) : StateFace()
data class ErrorProcessingFace(val error: Throwable) : StateFace()
object MoreThanOneFace : StateFace()
object NoFace : StateFace()

sealed class StateDocument
object ProcessingDocument : StateDocument()
data class DocumentProcessed(val documentText: String) : StateDocument()
data class ErrorProcessingDocument(val error: Throwable) : StateDocument()
object NoTextFound : StateDocument()
