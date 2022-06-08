package hr.nimai.spending.presentation.scan.components

import androidx.camera.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import hr.nimai.spending.domain.util.CameraUIAction
import hr.nimai.spending.domain.util.takePicture

@Composable
@ExperimentalGetImage
fun CameraView(
    onImageCaptured: (ImageProxy, Boolean) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    onExitClick: () -> Unit,
) {
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }

    CameraPreviewView(
        imageCapture = imageCapture
    ) { cameraUIAction ->
        when (cameraUIAction) {
            is CameraUIAction.OnCameraClick -> {
                imageCapture.takePicture(onImageCaptured, onError)
            }
            is CameraUIAction.OnExitClick -> {
                onExitClick()
            }
        }
    }
}