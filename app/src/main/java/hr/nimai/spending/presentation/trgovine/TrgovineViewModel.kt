package hr.nimai.spending.presentation.trgovine

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.use_case.TrgovineUseCases
import hr.nimai.spending.domain.util.RacunOrder
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
                _state.value = state.value.copy(
                    nazivTrgovine = event.value
                )
            }
            is TrgovineEvent.AddTrgovnaDialog -> {
                _state.value = state.value.copy(
                    isDialogOpen = true,
                    nazivTrgovine = "",
                    adresaTrgovine = "",
                )
            }
            is TrgovineEvent.SaveTrgovina -> {
                viewModelScope.launch {
                    trgovineUseCases.insertTrgovina(Trgovina(
                        naziv_trgovine = state.value.nazivTrgovine,
                        adresa = state.value.adresaTrgovine,
                        id_trgovine = 0,
                    ))
                }
                _state.value = state.value.copy(
                    isDialogOpen = false,
                )
            }
            is TrgovineEvent.DismissDialog -> {
                _state.value = state.value.copy(
                    isDialogOpen = false,
                )
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