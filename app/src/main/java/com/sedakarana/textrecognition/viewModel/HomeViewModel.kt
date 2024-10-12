package com.sedakarana.textrecognition.viewModel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application) {
    var hasImage = mutableStateOf(false)
    var imageUri = mutableStateOf<Uri?>(null)
    var textResult = mutableStateOf("")


    fun textRecognizer(context: Context) {
        var image = imageUri.value?.let { InputImage.fromFilePath(context, it) }
        var recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        if (image != null) {
            recognizer.process(image)
                .addOnSuccessListener {
                    progressText(context,it)
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Text could not be read", Toast.LENGTH_LONG)
                }
        }
    }

    private fun progressText(context: Context, it: Text?) {
        val blocks = it?.textBlocks
        if (blocks != null) {
            if (blocks.size == 0) { //GÖRSELDE TEXT YOK
                Toast.makeText(context, "No Text Detected In Image", Toast.LENGTH_LONG)
            }
        }
        val text = StringBuilder()
        if (blocks != null) {
            for (block in blocks) {
                for (line in block.lines) {
                    for (element in line.elements) {
                        text.append(element.text + " ")
                    }
                }
            }
        }
        textResult.value = text.toString()
    }

}
