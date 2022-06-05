package hr.nimai.spending.domain.use_case

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

@ExperimentalGetImage
class ParseBarcode {

    operator fun invoke(imageProxy: ImageProxy, onRecognitionComplete: (barcode: String) -> Unit) {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_EAN_13)
            .build()

        val scanner = BarcodeScanning.getClient(options)

        val inputImage = imageProxy.image?.let { InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees) }
        inputImage?.let {
            scanner.process(it).addOnSuccessListener { barcodes ->
                if (barcodes != null && barcodes.isNotEmpty()) {
                    val barcode = barcodes[0].rawValue?: ""
                    onRecognitionComplete(barcode)
                }
            }
        }
    }
}