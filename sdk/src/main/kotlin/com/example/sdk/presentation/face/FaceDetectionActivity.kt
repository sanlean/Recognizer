package com.example.sdk.presentation.face

import android.graphics.Bitmap
import android.widget.Button
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import com.example.sdk.R
import com.example.sdk.enum.ErrorType.*
import com.example.sdk.presentation.camera.CameraPreviewActivity
import com.example.sdk.enum.*
import com.example.sdk.listeners.FaceListener
import com.example.sdk.presentation.customviews.LoadingButton

internal class FaceDetectionActivity: CameraPreviewActivity() {

    val viewModel: FaceViewModel by viewModels{ FaceViewModelProvider() }

    private val faceCameraPreview: PreviewView by lazy { findViewById(R.id.faceCameraPreview) }
    private val btnTakeFacePicture: LoadingButton by lazy { findViewById(R.id.btnTakeFacePicture) }
    private val btnBack: Button by lazy { findViewById(R.id.btnBack) }

    override fun create() {
        super.create()
        setContentView(R.layout.activity_face_detection)
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
            cameraPreview = getPreview(faceCameraPreview)
        )
    }

    private fun setupButtons(){
        btnBack.setOnClickListener { finish() }
        btnTakeFacePicture.setClickListener {
            viewModel.takePicture()
        }
    }

    private fun setupCameraObserver(){
        viewModel.liveData.observe(this, { state ->
            when(state){
                is LoadingCamera -> btnTakeFacePicture.showLoading()
                is CameraLoaded -> btnTakeFacePicture.hideLoading()
                is ErrorLoadingCamera -> updateError(state.error, CAMERA_LOADING_ERROR)
                is LoadingTakingPicture -> btnTakeFacePicture.showLoading()
                is PictureTaken -> viewModel.processFace(state.image)
                is ErrorTakingPicture -> updateError(state.error, CAPTURE_PICTURE_ERROR)
            }
        })
    }

    private fun setupFaceObserver() {
        viewModel.faceLiveData.observe(this, { state ->
            when (state){
                is ProcessingFace -> btnTakeFacePicture.showLoading()
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