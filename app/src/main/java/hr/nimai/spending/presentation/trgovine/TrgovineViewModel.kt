package hr.nimai.spending.presentation.trgovine

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.use_case.TrgovineUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrgovineViewModel @Inject constructor(
    private val trgovineUseCases: TrgovineUseCases,
): ViewModel() {

    private val _state = mutableStateOf(TrgovineState())
    val state: State<TrgovineState> = _state

    private var trgovineJob: Job? = null

    init {
        getTrgovine()
    }

    fun onEvent(event: TrgovineEvent) {
        when (event) {
            is TrgovineEvent.EnteredAdresaTrgovine -> {
                _state.value = state.value.copy(
                    adresaTrgovine = event.value
                )
            }
            is TrgovineEvent.EnteredNazivTrgovine -> {
                if (event.value.isNotBlank()) {
                    _state.value = state.value.copy(
                        nazivTrgovine = event.value,
                        isNazivError = false,
                        showError = false
                    )
                } else {
                    _state.value = state.value.copy(
                        nazivTrgovine = event.value,
                        isNazivError = true,
                    )
                }
            }
            is TrgovineEvent.AddTrgovinaDialog -> {
                _state.value = state.value.copy(
                    isDialogOpen = true,
                    nazivTrgovine = "",
                    adresaTrgovine = "",
                    idTrgovine = 0,
                    isNazivError = true,
                )
            }
            is TrgovineEvent.SaveTrgovina -> {
                viewModelScope.launch {
                    if (!state.value.isNazivError) {
                        trgovineUseCases.insertTrgovina(Trgovina(
                            naziv_trgovine = state.value.nazivTrgovine,
                            adresa = state.value.adresaTrgovine,
                            id_trgovine = state.value.idTrgovine,
                        ))
                        _state.value = state.value.copy(
                            isDialogOpen = false,
                        )
                    } else {
                        _state.value = state.value.copy(
                            showError = true
                        )
                    }

                }

            }
            is TrgovineEvent.DismissDialog -> {
                _state.value = state.value.copy(
                    isDialogOpen = false,
                )
            }
            is TrgovineEvent.EditTrgovinaDialog -> {
                _state.value = state.value.copy(
                    nazivTrgovine = event.trgovina.naziv_trgovine,
                    adresaTrgovine = event.trgovina.adresa?:"",
                    idTrgovine = event.trgovina.id_trgovine,
                    isDialogOpen = true,
                    isNazivError = false
                )
            }
            is TrgovineEvent.DeleteTrgovina -> {
                viewModelScope.launch {
                    trgovineUseCases.deleteTrgovina(Trgovina(
                        id_trgovine = state.value.idTrgovine,
                        naziv_trgovine = state.value.nazivTrgovine,
                        adresa = state.value.adresaTrgovine,
                    ))
                    _state.value = state.value.copy(
                        isDialogOpen = false
                    )
                }
            }
        }
    }

    private fun getTrgovine() {
        trgovineJob?.cancel()
        trgovineJob = trgovineUseCases.getTrgovine()
            .onEach { trgovine ->
                _state.value = state.value.copy(
                    trgovine = trgovine,
                )
            }.launchIn(viewModelScope)
    }
}