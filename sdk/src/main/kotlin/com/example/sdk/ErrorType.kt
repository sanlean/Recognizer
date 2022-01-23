package com.example.sdk

import java.lang.Exception

enum class ErrorType {
    CAMERA_LOADING_ERROR,
    CAPTURE_PICTURE_ERROR,
    PROCESSING_IMAGE_ERROR;

    var error: Exception? = null
}