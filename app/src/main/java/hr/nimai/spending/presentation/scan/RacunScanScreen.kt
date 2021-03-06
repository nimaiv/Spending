package hr.nimai.spending.presentation.scan

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.nimai.spending.presentation.destinations.AddRacunScreenDestination
import hr.nimai.spending.presentation.scan.components.CameraView
import kotlinx.coroutines.flow.collectLatest

@Composable
@Destination
@ExperimentalGetImage
fun RacunScanScreen(
    navigator: DestinationsNavigator,
    viewModel: RacunScanViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is RacunScanViewModel.UiEvent.ScanComplete -> {
                    navigator.navigate(AddRacunScreenDestination(event.ocrText, event.imageByteArray))
                }
            }
        }
    }

    CameraView(
        onImageCaptured = { imageProxy, fromGallery ->
            viewModel.onEvent(ScanEvent.Scan(imageProxy))
        },
        onError = { },
        onExitClick = {
            navigator.navigateUp()
        }
    )
}