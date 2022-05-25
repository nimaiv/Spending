package hr.nimai.spending.presentation.add_racun

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.AddRacunUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddRacunViewModel @Inject constructor(
    private val addRacunUseCases: AddRacunUseCases
) : ViewModel() {

    private val _brojRacuna = mutableStateOf(RacunTextFieldState(
        hint = "Broj računa"
    ))
    val brojRacuna: State<RacunTextFieldState> = _brojRacuna

    private val _idTrgovine = mutableStateOf(RacunTextFieldState(
        hint = "Trgovina"
    ))
    val idTrgovine: State<RacunTextFieldState> = _idTrgovine

    private val _ukupanIznos = mutableStateOf(RacunTextFieldState(
        hint = "Ukupan iznos"
    ))
    val ukupanIznos: State<RacunTextFieldState> = _ukupanIznos

    private val _datumRacuna = mutableStateOf(RacunTextFieldState(
        hint = "Datum računa"
    ))
    val datumRacuna: State<RacunTextFieldState> = _datumRacuna

    private val _ocrTekst = mutableStateOf(RacunTextFieldState(
        hint = "Tekst skeniranog računa"
    ))
    val ocrTekst: State<RacunTextFieldState> = _ocrTekst

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFLow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveRacun
    }

}