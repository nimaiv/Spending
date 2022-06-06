package hr.nimai.spending.domain.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.common.internal.ImageConvertUtils
import java.io.ByteArrayOutputStream

@SuppressLint("UnsafeOptInUsageError")
fun getByteArray(imageProxy: ImageProxy): ByteArray {
    val bitmap: Bitmap = ImageConvertUtils.getInstance().getUpRightBitmap(
        InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees))
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray()
}