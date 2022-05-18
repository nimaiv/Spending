package hr.nimai.spending.presentation.racuni

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.RacunUseCases
import hr.nimai.spending.domain.util.OrderType
import hr.nimai.spending.domain.util.RacunOrder
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RacuniViewModel @Inject constructor(
    private val racunUseCases: RacunUseCases
) : ViewModel() {


    private val _state = mutableStateOf(RacuniState())
    val state: State<RacuniState> = _state

    private var getRacuniJob: Job? = null

    init {
        getRacuni(RacunOrder.Datum(OrderType.Descending))
    }

    fun onEvent(event: RacuniEvent) {
        when(event) {
            is RacuniEvent.Order -> {
                if(state.value.racunOrder::class == event.racunOrder::class &&
                        state.value.racunOrder.orderType == event.racunOrder.orderType) {
                    return
                }
                getRacuni(event.racunOrder)
            }
            is RacuniEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getRacuni(racunOrder: RacunOrder) {
        getRacuniJob?.cancel()
        getRacuniJob = racunUseCases.getRacuni(racunOrder)
            .onEach { racuni ->
                _state.value = state.value.copy(
                    racuni = racuni,
                    racunOrder = racunOrder
                )
            }.launchIn(viewModelScope)
    }
}