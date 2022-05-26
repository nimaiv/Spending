package hr.nimai.spending.presentation.add_racun

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.model.InvalidRacunException
import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.use_case.AddRacunUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRacunViewModel @Inject constructor(
    private val addRacunUseCases: AddRacunUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _brojRacuna = mutableStateOf(RacunTextFieldState(
        label = "Broj računa"
    ))
    val brojRacuna: State<RacunTextFieldState> = _brojRacuna

    private val _idTrgovine = mutableStateOf(RacunTextFieldState(
        label = "Trgovina"
    ))
    val idTrgovine: State<RacunTextFieldState> = _idTrgovine

    private val _ukupanIznos = mutableStateOf(RacunTextFieldState(
        label = "Ukupan iznos"
    ))
    val ukupanIznos: State<RacunTextFieldState> = _ukupanIznos

    private val _datumRacuna = mutableStateOf(RacunTextFieldState(
        label = "Datum računa"
    ))
    val datumRacuna: State<RacunTextFieldState> = _datumRacuna

    private val _ocrTekst = mutableStateOf(RacunTextFieldState(
        label = "Tekst skeniranog računa"
    ))
    val ocrTekst: State<RacunTextFieldState> = _ocrTekst

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        val ocrText = savedStateHandle.get<String>("ocrText")
        val racun = addRacunUseCases.readOCRToRacun(ocrText!!)
        _brojRacuna.value = brojRacuna.value.copy(
            text = racun.broj_racuna
        )
        _ukupanIznos.value = ukupanIznos.value.copy(
            text = racun.ukupan_iznos_racuna.toString()
        )
        _datumRacuna.value = datumRacuna.value.copy(
            text = racun.datum_racuna
        )
        _ocrTekst.value = ocrTekst.value.copy(
            text = racun.ocr_tekst!!
        )

    }

    fun onEvent(event: AddRacunEvent) {
        when (event) {
            is AddRacunEvent.EnteredBrojRacuna -> {
                _brojRacuna.value = brojRacuna.value.copy(
                    text = event.value
                )
            }
            is AddRacunEvent.EnteredDatumRacuna -> {
                _datumRacuna.value = datumRacuna.value.copy(
                    text = event.value
                )
            }
            is AddRacunEvent.EnteredTrgovina -> {
                _idTrgovine.value = idTrgovine.value.copy(
                    text = event.value
                )
            }
            is AddRacunEvent.EnteredUkupanIznos -> {
                _ukupanIznos.value = ukupanIznos.value.copy(
                    text = event.value
                )
            }
            AddRacunEvent.SaveRacun -> {
                viewModelScope.launch {
                    try {
                        addRacunUseCases.insertRacun(
                            Racun(
                                id_racuna = 0,
                                broj_racuna = brojRacuna.value.text,
                                id_trgovine = idTrgovine.value.text.toIntOrNull(),
                                ukupan_iznos_racuna = ukupanIznos.value.text.toDouble(),
                                datum_racuna = datumRacuna.value.text,
                                ocr_tekst = ocrTekst.value.text
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveRacun)
                    } catch(e: InvalidRacunException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Spremanje neuspješno"
                            )
                        )
                    }
                }
            }
        }
    }



    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveRacun: UiEvent()
    }

}