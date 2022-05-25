package hr.nimai.spending.presentation.scan

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.nimai.spending.presentation.destinations.AddRacunScreenDestination
import hr.nimai.spending.presentation.scan.components.CameraView
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Destination
@ExperimentalGetImage
fun RacunScanScreen(
    navigator: DestinationsNavigator,
    viewModel: RacunScanViewModel = hiltViewModel()
) {

    val cameraPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is RacunScanViewModel.UiEvent.ScanComplete -> {
                    navigator.navigate(AddRacunScreenDestination(event.ocrText))
                }
            }
        }
    }

    when (cameraPermissionState.status) {
        PermissionStatus.Granted -> {
            CameraView(
                onImageCaptured = { imageProxy, fromGallery ->
                    viewModel.onEvent(ScanEvent.Scan(imageProxy))
                },
                onError = { },
            )
        }
        is PermissionStatus.Denied -> {
            Column {
                val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                    "The camera is important for this app. Please grant the permission."
                } else {
                    "Camera permission required for this feature to be available. " +
                            "Please grant the permission"
                }
                Text(textToShow)
                Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text("Request permission")
                }
            }
        }
    }

}