package hr.nimai.spending.presentation.tipovi_proizvoda

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.model.TipProizvoda
import hr.nimai.spending.domain.use_case.TipoviProizvodaUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoviProizvodaViewModel @Inject constructor(
    private val tipoviProizvodaUseCases: TipoviProizvodaUseCases
): ViewModel() {

    private val _state = mutableStateOf(TipoviProizvodaState())
    val state: State<TipoviProizvodaState> = _state

    private var tipoviProizvodaJob: Job? = null

    init {
        getTipoviProizvoda()
    }

    fun onEvent(event: TipoviProizvodaEvent) {
        when (event) {
            is TipoviProizvodaEvent.AddTipProizvodaDialog -> {
                _state.value = state.value.copy(
                    isDialogOpen = true,
                    nazivTipaProizvoda = "",
                    idTipaProizvoda = 0,
                    isEdit = false,
                )
            }
            is TipoviProizvodaEvent.DismissDialog -> {
                _state.value = state.value.copy(
                    isDialogOpen = false,
                )
            }
            is TipoviProizvodaEvent.EditTipProizvodaDialog -> {
                _state.value = state.value.copy(
                    isDialogOpen = true,
                    nazivTipaProizvoda = event.tipProizvoda.naziv_tipa_proizvoda,
                    idTipaProizvoda = event.tipProizvoda.id_tipa_proizvoda,
                    isEdit = true,
                )
            }
            is TipoviProizvodaEvent.EnteredNaziv -> {
                _state.value = state.value.copy(
                    nazivTipaProizvoda = event.value
                )
            }
            is TipoviProizvodaEvent.SaveTipProizvoda -> {
                _state.value = state.value.copy(
                    isDialogOpen = false
                )
                viewModelScope.launch {
                    tipoviProizvodaUseCases.insertTipProizvoda(TipProizvoda(
                        id_tipa_proizvoda = state.value.idTipaProizvoda,
                        naziv_tipa_proizvoda = state.value.nazivTipaProizvoda
                    ))
                }
            }
            is TipoviProizvodaEvent.DeleteTipProizvoda -> {
                _state.value = state.value.copy(
                    isDialogOpen = false
                )
                viewModelScope.launch {
                    tipoviProizvodaUseCases.deleteTipProizvoda(state.value.idTipaProizvoda)
                }
            }
        }
    }

    private fun getTipoviProizvoda() {
        tipoviProizvodaJob?.cancel()
        tipoviProizvodaJob = tipoviProizvodaUseCases.getTipoviProizvoda()
            .onEach { tipoviProizvoda ->
                _state.value = state.value.copy(
                    tipoviProizvoda = tipoviProizvoda,
                )
            }.launchIn(viewModelScope)
    }

}