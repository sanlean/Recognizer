package com.example.sdk.face

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sdk.*
import com.example.sdk.CameraViewModel
import com.example.sdk.extensions.cropBitmap
import com.example.sdk.extensions.processFace
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.launch
import java.lang.Exception

internal class FaceViewModel: CameraViewModel() {

    private val _faceLiveData = MutableLiveData<StateFace>()
    val faceLiveData: LiveData<StateFace> = _faceLiveData

    fun processFace(image: InputImage){
        _faceLiveData.value = ProcessingFace
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        val detector = FaceDetection.getClient(highAccuracyOpts)
        viewModelScope.launch {
            try {
                val faces = detector.processFace(image)
                when(faces.size) {
                    0 -> _faceLiveData.value = NoFace
                    1 -> {
                        val croppedFace = image.bitmapInternal.cropBitmap(faces[0].boundingBox)
                        _faceLiveData.value = FacePrecessed(croppedFace)
                    }
                    else -> _faceLiveData.value = MoreThanOneFace
                }
            }catch (e: Exception){
                _faceLiveData.value = ErrorProcessingFace(e)
            }
        }
    }
}