package hr.nimai.spending.presentation.scan

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.RacunScanUseCases
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class RacunScanViewModel @Inject constructor(
    private val racunScanUseCases: RacunScanUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ScanState())
    val state: State<ScanState> = _state


    @SuppressLint("UnsafeOptInUsageError")
    fun onEvent(event: ScanEvent) {
        when(event) {
            is ScanEvent.Scan -> {
                val ocrText = racunScanUseCases.parseScanRacuna(event.imageProxy)
                _state.value = state.value.copy(
                    ocrText = ocrText
                )
                event.imageProxy.close()
            }
        }
    }
}