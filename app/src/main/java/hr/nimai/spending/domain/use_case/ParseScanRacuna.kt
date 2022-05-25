package hr.nimai.spending.domain.use_case

import android.content.ContentValues.TAG
import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@ExperimentalGetImage
class ParseScanRacuna {
    operator fun invoke(imageProxy: ImageProxy, onRecognitionComplete: (ocrText: String) -> Unit) {

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val inputImage = imageProxy.image?.let { InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees) }
        inputImage?.let {
            recognizer.process(it).addOnSuccessListener { visionText ->
                val ocrText = visionText.text
                Log.d(TAG, ocrText)
                onRecognitionComplete(ocrText)
            }
        }
    }
}