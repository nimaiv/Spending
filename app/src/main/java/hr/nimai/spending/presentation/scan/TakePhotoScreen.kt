package hr.nimai.spending.presentation.scan

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import hr.nimai.spending.presentation.destinations.ProizvodViewScreenDestination
import hr.nimai.spending.presentation.scan.components.CameraView
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnsafeOptInUsageError")
@Destination
@Composable
fun TakePhotoScreen(
    navigator: DestinationsNavigator,
    viewModel: TakePhotoViewModel = hiltViewModel(),
    resultBackNavigator: ResultBackNavigator<ByteArray>
) {

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is TakePhotoViewModel.UiEvent.ReturnImage -> {
                    resultBackNavigator.navigateBack(event.imageByteArray)
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