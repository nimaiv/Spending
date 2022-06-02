package hr.nimai.spending.presentation.proizvod_view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.ProizvodViewUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProizvodViewViewModel @Inject constructor(
    private val proizvodViewUseCases: ProizvodViewUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ProizvodViewState())
    val state: State<ProizvodViewState> = _state

    init {
        val idProizvoda: Int = savedStateHandle.get<Int>("idProizvoda")!!
        viewModelScope.launch {
            proizvodViewUseCases.getProizvod(idProizvoda).also { proizvod ->
                _state.value = state.value.copy(
                    idProizvoda = proizvod.id_proizvoda,
                    nazivProizvoda = proizvod.naziv_proizvoda,
                    skraceniNazivProizvoda = proizvod.skraceni_naziv_proizvoda,
                    barkod = proizvod.barkod,
                    uriSlikeProizvoda = proizvod.uri_slike,
                    idTipaProizvoda = proizvod.tip_proizvoda
                )
            }
            proizvodViewUseCases.getKupnjeProizvoda(idProizvoda).also { kupnjeProizvoda ->
                _state.value = state.value.copy(
                    kupnje = kupnjeProizvoda
                )
            }
            proizvodViewUseCases.getTipoviProizvoda().also { tipoviProizvoda ->
                _state.value = state.value.copy(
                    tipoviProizvoda = tipoviProizvoda
                )
            }
            if (state.value.idTipaProizvoda != null) {
                _state.value = state.value.copy(
                    nazivTipaProizvoda = state.value.tipoviProizvoda.find {
                        it.id_tipa_proizvoda == state.value.idTipaProizvoda
                    }?.naziv_tipa_proizvoda ?: ""
                )
            }
        }
    }

    fun onEvent(event: ProizvodViewEvent) {
        when (event) {
            is ProizvodViewEvent.OnDropdownItemSelect -> {
                _state.value = state.value.copy(
                    idTipaProizvoda = event.tipProizvoda.id_tipa_proizvoda,
                    nazivTipaProizvoda = event.tipProizvoda.naziv_tipa_proizvoda
                )
            }
        }
    }
}