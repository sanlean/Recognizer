package com.example.sdk.document

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sdk.camera.CameraViewModel
import com.example.sdk.enum.*
import com.example.sdk.extensions.isTextEmpty
import com.example.sdk.extensions.processText
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.launch
import java.lang.Exception

internal class DocumentViewModel : CameraViewModel() {

    private val _documentLiveData = MutableLiveData<StateDocument>()
    val documentLiveData: LiveData<StateDocument> = _documentLiveData

    fun processDocumentText(
        image: InputImage
    ){
        _documentLiveData.value = ProcessingDocument
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        viewModelScope.launch {
            try {
                val text = recognizer.processText(image)
                if (text.isTextEmpty()){
                    _documentLiveData.value = NoTextFound
                }else{
                    _documentLiveData.value = DocumentProcessed(text.text)
                }
            }catch (e: Exception){
                _documentLiveData.value = ErrorProcessingDocument(e)
            }
        }
    }
}