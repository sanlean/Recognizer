package com.example.sdk.presentation.document

import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import com.example.sdk.presentation.camera.CameraPreviewActivity
import com.example.sdk.databinding.ActivityDocumentDetectionBinding
import com.example.sdk.enum.*
import com.example.sdk.enum.ErrorType.*
import com.example.sdk.listeners.DocumentListener

internal class DocumentDetectionActivity : CameraPreviewActivity() {

    private lateinit var binding: ActivityDocumentDetectionBinding
    private val viewModel: DocumentViewModel by viewModels()

    override fun create() {
        super.create()
        binding = ActivityDocumentDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupCameraObserver()
        setupDocumentObserver()
        setupCamera()
        setupButtons()
    }

    private fun setupCamera(){
        viewModel.startCamera(
            context = this,
            lifecycleOwner = this,
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
            cameraPreview = getPreview(binding.cameraPreview)
        )
    }

    private fun setupButtons(){
        binding.btnBack.setOnClickListener { finish() }
        binding.btnTakePicture.setClickListener {
            viewModel.takePicture()
        }
    }

    private fun setupCameraObserver() {
        viewModel.liveData.observe(this, { state ->
            when(state){
                is LoadingCamera -> binding.btnTakePicture.showLoading()
                is CameraLoaded -> binding.btnTakePicture.hideLoading()
                is LoadingTakingPicture -> binding.btnTakePicture.showLoading()
                is ErrorLoadingCamera -> updateError(state.error, CAMERA_LOADING_ERROR)
                is ErrorTakingPicture -> updateError(state.error, CAPTURE_PICTURE_ERROR)
                is PictureTaken -> viewModel.processDocumentText(state.image)
            }
        })
    }

    private fun setupDocumentObserver() {
        viewModel.documentLiveData.observe(this, { state ->
            when (state){
                is ProcessingDocument -> binding.btnTakePicture.showLoading()
                is DocumentProcessed -> showDocumentResult(state.documentText)
                is ErrorProcessingDocument -> updateError(state.error, PROCESSING_IMAGE_ERROR)
                is NoTextFound -> showNoDocumentFound()
            }
        })
    }

    private fun showDocumentResult(documentText: String){
        documentListener?.textFoundOnDocument(documentText)
        finish()
    }

    private fun showNoDocumentFound(){
        documentListener?.noTextFound()
        finish()
    }

    internal companion object {
        private const val TAG = "DocumentDetectionCamera"
        var documentListener: DocumentListener? = null
    }
}