package hr.nimai.spending.presentation.scan

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import hr.nimai.spending.presentation.scan.components.CameraView
import kotlinx.coroutines.flow.collectLatest


@Composable
@Destination
@ExperimentalGetImage
fun BarcodeScanScreen(
    resultBackNavigator: ResultBackNavigator<String>,
    viewModel: BarcodeScanViewModel = hiltViewModel()
) {


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is BarcodeScanViewModel.UiEvent.ScanComplete -> {
                    resultBackNavigator.navigateBack(event.barcode)
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
            resultBackNavigator.navigateBack()
        }
    )

}