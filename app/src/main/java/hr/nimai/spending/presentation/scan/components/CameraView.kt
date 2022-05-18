package hr.nimai.spending.presentation.scan.components

import androidx.camera.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.nimai.spending.domain.util.CameraUIAction
import hr.nimai.spending.domain.util.takePicture
import hr.nimai.spending.presentation.add_racun.AddRacunViewModel
import hr.nimai.spending.presentation.destinations.AddRacunScreenDestination
import hr.nimai.spending.presentation.scan.RacunScanViewModel
import kotlinx.coroutines.awaitAll
import java.util.concurrent.TimeUnit

@Composable
@ExperimentalGetImage
fun CameraView(
    onImageCaptured: (ImageProxy, Boolean) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().build()
    }

    /* TODO: Add gallery image load
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageProxy: ImageProxy ->
        if (uri != null) onImageCaptured(true)
    }
    */

    CameraPreviewView(
        imageCapture = imageCapture
    ) { cameraUIAction ->
        when (cameraUIAction) {
            is CameraUIAction.OnCameraClick -> {
                imageCapture.takePicture(onImageCaptured, onError)

            }
            is CameraUIAction.OnGalleryViewClick -> {

            }
            is CameraUIAction.OnExitClick -> {

            }
        }
    }


}