package hr.nimai.spending.presentation.proizvodi

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
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

    fun onEvent(event: ProizvodiEvent) {
        when (event) {
            is ProizvodiEvent.OnSearchQueryChanged -> {
                _state.value = state.value.copy(
                    query = event.value,
                    proizvodiShown = state.value.proizvodi.filter {
                        it.naziv_proizvoda.contains(event.value, ignoreCase = true)
                    }
                )
            }
        }
    }

    private fun getProizvodi() {
        getProizvodiJob?.cancel()
        getProizvodiJob = proizvodiUseCases.getProizvodi()
            .onEach { proizvodi ->
                _state.value = state.value.copy(
                    proizvodi = proizvodi,
                    proizvodiShown = proizvodi
                )
            }.launchIn(viewModelScope)
    }
}