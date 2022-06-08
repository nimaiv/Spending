package hr.nimai.spending.presentation.potrosnja

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.PotrosnjaUseCases
import hr.nimai.spending.domain.util.getDateFromMillis
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PotrosnjaViewModel @Inject constructor(
    private val potrosnjaUseCases: PotrosnjaUseCases
): ViewModel() {


    private val _state = mutableStateOf(PotrosnjaState())
    val state: State<PotrosnjaState> = _state

    fun onEvent(event: PotrosnjaEvent) {
        when (event) {
            is PotrosnjaEvent.OnDatePick -> {
                _state.value = state.value.copy(
                    startDate = getDateFromMillis(event.dates.first),
                    endDate = getDateFromMillis(event.dates.second)
                )
                if (state.value.id != null && state.value.type != null) {
                    viewModelScope.launch {
                        val spending = potrosnjaUseCases.getSpending(Pair(state.value.type!!, state.value.id!!), state.value.startDate, state.value.endDate)
                        _state.value = state.value.copy(
                            naziv = spending.first,
                            items = spending.second,
                            ukupno = spending.second.sumOf { it.second }
                        )
                    }
                }
            }
            is PotrosnjaEvent.DisplaySpending -> {
                viewModelScope.launch {
                    val spending = potrosnjaUseCases.getSpending(Pair(event.item.type, event.item.id), state.value.startDate, state.value.endDate)
                    _state.value = state.value.copy(
                        naziv = spending.first,
                        items = spending.second,
                        id = event.item.id,
                        type = event.item.type,
                        ukupno = spending.second.sumOf { it.second }
                    )
                }
            }
        }
    }
}