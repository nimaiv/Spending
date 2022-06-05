package hr.nimai.spending.presentation.scan

import android.Manifest
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import hr.nimai.spending.presentation.scan.components.CameraView
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Destination
@ExperimentalGetImage
fun BarcodeScanScreen(
    resultBackNavigator: ResultBackNavigator<String>,
    viewModel: BarcodeScanViewModel = hiltViewModel()
) {

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is BarcodeScanViewModel.UiEvent.ScanComplete -> {
                    resultBackNavigator.navigateBack(event.barcode)
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
                onExitClick = {
                    resultBackNavigator.navigateBack()
                }
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