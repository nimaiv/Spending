package hr.nimai.spending.presentation.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.util.getByteArray
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TakePhotoViewModel @Inject constructor(): ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: ScanEvent) {
        when (event) {
            is ScanEvent.Scan -> {
                val imageByteArray = getByteArray(event.imageProxy)
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ReturnImage(imageByteArray))
                }
            }
        }
    }

    sealed class UiEvent {
        data class ReturnImage(val imageByteArray: ByteArray): UiEvent()
    }
}