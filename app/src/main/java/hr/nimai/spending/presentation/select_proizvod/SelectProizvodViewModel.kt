package hr.nimai.spending.presentation.select_proizvod

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.ProizvodiUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SelectProizvodViewModel @Inject constructor(
    private val proizvodiUseCases: ProizvodiUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(SelectProizvodiState())
    val state: State<SelectProizvodiState> = _state

    private var getProizvodiJob: Job? = null

    init {
        val filterIds = savedStateHandle.get<IntArray>("filterProizovdi")
        if (filterIds != null) {
            _state.value = state.value.copy(
                idFilterProizvoda = filterIds.toList()
            )
        }
        getProizvodi()
    }

    fun onEvent(event: SelectProizvodiEvent) {
        when (event) {
            is SelectProizvodiEvent.OnSearchQueryChanged -> {
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
                    proizvodi = proizvodi.filter { !state.value.idFilterProizvoda.contains(it.id_proizvoda) },
                    proizvodiShown = proizvodi.filter { !state.value.idFilterProizvoda.contains(it.id_proizvoda) }
                )
            }.launchIn(viewModelScope)
    }
}