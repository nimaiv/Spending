package hr.nimai.spending.presentation.select_spending

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.SelectSpendingUseCases
import hr.nimai.spending.domain.util.ItemHolder
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectSpendingViewModel @Inject constructor(
    private val selectSpendingUseCases: SelectSpendingUseCases
): ViewModel() {


    private val _state = mutableStateOf(SelectSpendingState())
    val state: State<SelectSpendingState> = _state


    init {
        viewModelScope.launch {
            _state.value = state.value.copy(
                proizvodi = selectSpendingUseCases.getProizvodiSuspend().sortedBy { it.naziv_proizvoda }
                    .map { ItemHolder(id = it.id_proizvoda, naziv = it.naziv_proizvoda) },
                trgovine = selectSpendingUseCases.getTrgovineSuspend().sortedBy { it.naziv_trgovine }
                    .map { ItemHolder(id = it.id_trgovine, naziv = it.naziv_trgovine) },
                tipoviProizvoda = selectSpendingUseCases.getTipoviProizvodaSuspend().sortedBy { it.naziv_tipa_proizvoda }
                    .map { ItemHolder(id = it.id_tipa_proizvoda, naziv = it.naziv_tipa_proizvoda) }
            )
        }
    }

    fun onEvent(event: SelectSpendingEvent) {
        when(event) {
            SelectSpendingEvent.SelectProizvodi -> {
                _state.value = state.value.copy(
                    content = Screens.PROIZVODI
                )
            }
            SelectSpendingEvent.SelectTipoviProizvoda -> {
                _state.value = state.value.copy(
                    content = Screens.TIPOVIPROIZVODA
                )
            }
            SelectSpendingEvent.SelectTrgovine -> {
                _state.value = state.value.copy(
                    content = Screens.TRGOVINE
                )
            }
        }
    }
}