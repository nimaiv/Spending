package hr.nimai.spending.presentation.racun_proizvodi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.RacunProizvodiUseCases
import javax.inject.Inject

@HiltViewModel
class RacunProizvodiViewModel @Inject constructor(
    racunProizvodiUseCases: RacunProizvodiUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = mutableStateOf(RacunProizvodiState())
    val state: State<RacunProizvodiState> = _state

    init {

    }

    fun onEvent(event: RacunProizvodiEvent) {
        when (event) {

        }
    }
}