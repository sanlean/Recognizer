package com.example.sdk.face

import android.graphics.Bitmap
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import com.example.sdk.*
import com.example.sdk.ErrorType.*
import com.example.sdk.databinding.ActivityFaceDetectionBinding
import com.example.sdk.document.DocumentDetectionActivity

internal class FaceDetectionActivity: CameraPreviewActivity() {

    private val viewModel: FaceViewModel by viewModels()

    private lateinit var binding: ActivityFaceDetectionBinding

    override fun create() {
        super.create()
        binding = ActivityFaceDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupCameraObserver()
        setupFaceObserver()
        setupCamera()
        setupButtons()
    }

    private fun setupCamera(){
        viewModel.startCamera(
            context = this,
            lifecycleOwner = this,
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA,
            cameraPreview = getPreview(binding.cameraPreview)
        )
    }

    private fun setupButtons(){
        binding.btnBack.setOnClickListener { finish() }
        binding.btnTakePicture.setClickListener {
            viewModel.takePicture()
        }
    }

    private fun setupCameraObserver(){
        viewModel.liveData.observe(this, { state ->
            when(state){
                is LoadingCamera -> binding.btnTakePicture.showLoading()
                is CameraLoaded -> binding.btnTakePicture.hideLoading()
                is ErrorLoadingCamera -> updateError(state.error, CAMERA_LOADING_ERROR)
                is LoadingTakingPicture -> binding.btnTakePicture.showLoading()
                is PictureTaken -> viewModel.processFace(state.image)
                is ErrorTakingPicture -> updateError(state.error, CAPTURE_PICTURE_ERROR)
            }
        })
    }

    private fun setupFaceObserver() {
        viewModel.faceLiveData.observe(this, { state ->
            when (state){
                is ProcessingFace -> binding.btnTakePicture.showLoading()
                is FacePrecessed -> showFaceResult(state.bitmap)
                is ErrorProcessingFace -> updateError(state.error, PROCESSING_IMAGE_ERROR)
                is MoreThanOneFace -> showMoreThanOneFaceFound()
                is NoFace -> showNoFaceFound()
            }
        })
    }

    private fun showFaceResult(facePicture: Bitmap){
        faceListener?.facePictureFound(facePicture)
        finish()
    }

    private fun showNoFaceFound(){
        faceListener?.noFaceFound()
        finish()
    }

    private fun showMoreThanOneFaceFound(){
        faceListener?.moreThanOneFaceFound()
        finish()
    }

    internal companion object{
        var faceListener: FaceListener? = null

    }
}