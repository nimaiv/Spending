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
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        val ocrText = savedStateHandle.get<String>("ocrText")
        val racun = addRacunUseCases.readOCRToRacun(ocrText!!)
        _brojRacuna.value = brojRacuna.value.copy(
            text = racun.broj_racuna,
            isHintVisible = false
        )
        _ukupanIznos.value = ukupanIznos.value.copy(
            text = racun.ukupan_iznos_racuna.toString(),
            isHintVisible = false
        )
        _datumRacuna.value = datumRacuna.value.copy(
            text = racun.datum_racuna,
            isHintVisible = false
        )
        _ocrTekst.value = ocrTekst.value.copy(
            text = racun.ocr_tekst!!,
            isHintVisible = false
        )

    }

    fun onEvent(event: AddRacunEvent) {
        when (event) {
            is AddRacunEvent.ChangeBrojRacunaFocus -> {
                _brojRacuna.value = brojRacuna.value.copy(
                    isHintVisible = !event.focusState.isFocused && brojRacuna.value.text.isBlank()
                )
            }
            is AddRacunEvent.ChangeDatumRacunaFocus -> {
                _datumRacuna.value = datumRacuna.value.copy(
                    isHintVisible = !event.focusState.isFocused && datumRacuna.value.text.isBlank()
                )
            }
            is AddRacunEvent.ChangeTrgovinaFocus -> {
                _idTrgovine.value = idTrgovine.value.copy(
                    isHintVisible = !event.focusState.isFocused && idTrgovine.value.text.isBlank()
                )
            }
            is AddRacunEvent.ChangeUkupanIznosFocus -> {
                _ukupanIznos.value = ukupanIznos.value.copy(
                    isHintVisible = !event.focusState.isFocused && ukupanIznos.value.text.isBlank()
                )
            }
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
                                id_trgovine = idTrgovine.value.text.toInt(),
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