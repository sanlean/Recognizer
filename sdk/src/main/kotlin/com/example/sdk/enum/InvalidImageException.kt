package com.example.sdk.enum

import java.io.IOException

class InvalidImageException: IOException("Bitmap image not available. Verify camera hardware and permissions") {
}