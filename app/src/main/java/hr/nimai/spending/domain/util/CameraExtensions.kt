package hr.nimai.spending.domain.util

import android.content.Context
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ExperimentalGetImage
fun ImageCapture.takePicture(
    onImageCaptured: (ImageProxy, Boolean) -> Unit,
    onError: (ImageCaptureException) -> Unit
): ExecutorService? {
    val executor = Executors.newSingleThreadExecutor()
    this.takePicture(
        executor,
        object: ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(imageProxy: ImageProxy) {
                onImageCaptured(imageProxy, false)
            }
            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
    return executor
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}