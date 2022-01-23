package com.example.sdk

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdk.extensions.getCameraProvider
import com.example.sdk.extensions.takePicture
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal open class CameraViewModel : ViewModel() {
    private val imageCapture: ImageCapture = ImageCapture.Builder().build()
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    private val _liveData = MutableLiveData<StateCamera>()
    val liveData: LiveData<StateCamera> = _liveData

    fun startCamera(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        cameraSelector: CameraSelector,
        cameraPreview: Preview
    ) {
        _liveData.value = LoadingCamera
        viewModelScope.launch {
            val cameraProvider = context.getCameraProvider(executor)
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, cameraPreview, imageCapture
                )
                _liveData.value = CameraLoaded

            } catch (exc: Exception) {
                _liveData.value = ErrorLoadingCamera(exc)
            }
        }
    }

    fun takePicture() {
        _liveData.value = LoadingTakingPicture
        viewModelScope.launch {
            try {
                val image = imageCapture.takePicture(executor)
                _liveData.value = PictureTaken(image)
            }catch (e: ImageCaptureException){
                _liveData.value = ErrorTakingPicture(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }
}
