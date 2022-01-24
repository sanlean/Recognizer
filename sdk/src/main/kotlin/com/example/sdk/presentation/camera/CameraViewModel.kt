package com.example.sdk.presentation.camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.lifecycle.*
import com.example.sdk.domain.CameraBindUseCase
import com.example.sdk.domain.CameraProviderUseCase
import com.example.sdk.domain.TakePictureUseCase
import com.example.sdk.enum.*
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

internal open class CameraViewModel(
    private val cameraProviderUseCase: CameraProviderUseCase,
    private val cameraBindUseCase: CameraBindUseCase,
    private val takePictureUseCase: TakePictureUseCase
) : ViewModel() {

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
            val cameraProvider = cameraProviderUseCase(context, executor)
            try {
                cameraBindUseCase(
                    lifecycleOwner,
                    cameraProvider,
                    cameraSelector,
                    cameraPreview,
                    imageCapture
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
                val image = takePictureUseCase(imageCapture, executor)
                _liveData.value = PictureTaken(image)
            } catch (e: Exception) {
                _liveData.value = ErrorTakingPicture(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        executor.shutdown()
    }
}
