package hr.nimai.spending.presentation.scan

import androidx.camera.core.ImageProxy

sealed class ScanEvent {
    data class Scan(val imageProxy: ImageProxy): ScanEvent()
}
