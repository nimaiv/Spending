package hr.nimai.spending.presentation.proizvod_view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.model.Proizvod
import hr.nimai.spending.domain.use_case.ProizvodViewUseCases
import hr.nimai.spending.presentation.add_racun.AddRacunViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProizvodViewViewModel @Inject constructor(
    private val proizvodViewUseCases: ProizvodViewUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ProizvodViewState())
    val state: State<ProizvodViewState> = _state

    private val _eventFlow = MutableSharedFlow<ProizvodViewViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
            is ProizvodViewEvent.ToggleEdit -> {
                if (!state.value.isEditEnabled) {
                    _state.value = state.value.copy(
                        isEditEnabled = true,
                        buttonText = "Spremi"
                    )
                } else {
                    _state.value = state.value.copy(
                        isEditEnabled = false,
                        buttonText = "Uredi"
                    )
                    viewModelScope.launch {
                        proizvodViewUseCases.insertProizvod(Proizvod(
                            id_proizvoda = state.value.idProizvoda,
                            naziv_proizvoda = state.value.nazivProizvoda,
                            skraceni_naziv_proizvoda = state.value.skraceniNazivProizvoda,
                            tip_proizvoda = state.value.idTipaProizvoda,
                            barkod = state.value.barkod,
                            uri_slike = state.value.uriSlikeProizvoda
                        ))
                    }
                }
            }
            is ProizvodViewEvent.EnteredBarkod -> {
                _state.value = state.value.copy(
                    barkod = event.value
                )
            }
            is ProizvodViewEvent.EnteredNazivProizvoda -> {
                _state.value = state.value.copy(
                    nazivProizvoda = event.value
                )
            }
            is ProizvodViewEvent.EnteredSkraceniNazivProizvoda -> {
                _state.value = state.value.copy(
                    skraceniNazivProizvoda = event.value
                )
            }
            is ProizvodViewEvent.DeleteProizvod -> {
                viewModelScope.launch {
                    proizvodViewUseCases.deleteProizvod(Proizvod(
                        id_proizvoda = state.value.idProizvoda,
                        naziv_proizvoda = state.value.nazivProizvoda,
                        skraceni_naziv_proizvoda = state.value.skraceniNazivProizvoda,
                        tip_proizvoda = state.value.idTipaProizvoda,
                        barkod = state.value.barkod,
                        uri_slike = state.value.uriSlikeProizvoda
                    ))
                    _eventFlow.emit(UiEvent.DeletedProizvod)
                }
            }
            is ProizvodViewEvent.SelectTipProizvoda -> {
                _state.value = state.value.copy(
                    idTipaProizvoda = event.value.id_tipa_proizvoda,
                    nazivProizvoda = event.value.naziv_tipa_proizvoda
                )
            }
        }
    }

    sealed class UiEvent {
        object DeletedProizvod: UiEvent()
    }
}