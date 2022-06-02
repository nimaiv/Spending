package hr.nimai.spending.presentation.proizvodi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.ProizvodiUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProizvodiViewModel @Inject constructor(
    private val proizvodiUseCases: ProizvodiUseCases
) : ViewModel() {


    private val _state = mutableStateOf(ProizvodiState())
    val state: State<ProizvodiState> = _state

    private var getProizvodiJob: Job? = null

    init {
        getProizvodi()
    }

    fun onEvent(proizvodiEvent: ProizvodiEvent) {

    }

    private fun getProizvodi() {
        getProizvodiJob?.cancel()
        getProizvodiJob = proizvodiUseCases.getProizvodi()
            .onEach { proizvodi ->
                _state.value = state.value.copy(
                    proizvodi = proizvodi
                )
            }.launchIn(viewModelScope)
    }
}