package hr.nimai.spending.presentation.scan

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.BarcodeScanUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BarcodeScanViewModel @Inject constructor(
    private val barcodeScanUseCases: BarcodeScanUseCases,
): ViewModel() {

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
                    barcodeScanUseCases.parseBarcode(event.imageProxy) { barcode ->
                        event.imageProxy.close()
                        viewModelScope.launch {
                            _eventFlow.emit(UiEvent.ScanComplete(barcode))
                        }
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ScanComplete(val barcode: String): UiEvent()
    }
}