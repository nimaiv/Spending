package hr.nimai.spending.presentation.scan

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.media.Image
import android.os.Parcel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.common.internal.ImageConvertUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.RacunScanUseCases
import hr.nimai.spending.domain.util.getByteArray
import hr.nimai.spending.domain.util.toBitmap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RacunScanViewModel @Inject constructor(
    private val racunScanUseCases: RacunScanUseCases
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    @SuppressLint("UnsafeOptInUsageError")
    fun onEvent(event: ScanEvent) {
        when(event) {
            /**
             * sussy but works
             */
            is ScanEvent.Scan -> {
                viewModelScope.launch {
                    racunScanUseCases.parseScanRacuna(event.imageProxy) { ocrText ->
                        val imageByteArray = getByteArray(event.imageProxy)
                        event.imageProxy.close()
                        viewModelScope.launch {
                            _eventFlow.emit(UiEvent.ScanComplete(ocrText, imageByteArray))
                        }
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ScanComplete(val ocrText: String, val imageByteArray: ByteArray): UiEvent()
    }
}