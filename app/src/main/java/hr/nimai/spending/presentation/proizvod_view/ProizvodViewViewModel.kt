package hr.nimai.spending.presentation.proizvod_view

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.model.Proizvod
import hr.nimai.spending.domain.use_case.ProizvodViewUseCases
import hr.nimai.spending.presentation.add_racun.AddRacunEvent
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

    private val _eventFlow = MutableSharedFlow<UiEvent>()
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
                    idTipaProizvoda = proizvod.tip_proizvoda,
                )
            }
            if (!state.value.uriSlikeProizvoda.isNullOrBlank()) {
                _eventFlow.emit(UiEvent.LoadSlika)
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
                        buttonText = "Spremi",
                        buttonColor = Color(0xFF38903C)
                    )
                } else {
                    _state.value = state.value.copy(
                        isEditEnabled = false,
                        buttonText = "Uredi",
                        buttonColor = Color(0xFF1678A3)
                    )
                    viewModelScope.launch {
                        proizvodViewUseCases.updateProizvod(Proizvod(
                            id_proizvoda = state.value.idProizvoda,
                            naziv_proizvoda = state.value.nazivProizvoda,
                            skraceni_naziv_proizvoda = state.value.skraceniNazivProizvoda,
                            tip_proizvoda = state.value.idTipaProizvoda,
                            barkod = state.value.barkod,
                            uri_slike = state.value.uriSlikeProizvoda
                        ))
                    }
                    if (state.value.slika != null) {
                        event.context.openFileOutput(state.value.uriSlikeProizvoda, Context.MODE_PRIVATE).use {
                            it.write(state.value.slika)
                        }
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
                if (!state.value.uriSlikeProizvoda.isNullOrBlank()) {
                    event.context.deleteFile(state.value.uriSlikeProizvoda)
                }
                viewModelScope.launch {
                    proizvodViewUseCases.deleteProizvod(Proizvod(
                        id_proizvoda = state.value.idProizvoda,
                        naziv_proizvoda = state.value.nazivProizvoda,
                        skraceni_naziv_proizvoda = state.value.skraceniNazivProizvoda,
                        tip_proizvoda = state.value.idTipaProizvoda,
                        barkod = state.value.barkod,
                        uri_slike = ""
                    ))
                    _eventFlow.emit(UiEvent.DeletedProizvod(state.value.idProizvoda))
                }
            }
            is ProizvodViewEvent.SelectTipProizvoda -> {
                _state.value = state.value.copy(
                    idTipaProizvoda = event.value.id_tipa_proizvoda,
                    nazivTipaProizvoda = event.value.naziv_tipa_proizvoda,
                )
            }
            is ProizvodViewEvent.GetDataWithBarcode -> {
                proizvodViewUseCases.getProizvodInfoFromBarcode(event.barcode) { proizvodi ->
                    if (proizvodi != null) {
                        val proizvod = proizvodi.products[0]
                        proizvodViewUseCases.downloadImage(proizvod.images[0]) { image ->
                            _state.value = state.value.copy(
                                barkod = event.barcode,
                                nazivProizvoda = proizvod.title,
                                uriSlikeProizvoda = "${event.barcode}.jpg",
                                slika = image,
                            )
                        }
                    } else {
                        _state.value = state.value.copy(
                            barkod = event.barcode,
                        )
                        viewModelScope.launch {
                            _eventFlow.emit(UiEvent.ShowToast("Nije pronađen proizvod s barkodom"))
                        }
                    }
                }
            }
            is ProizvodViewEvent.LoadSlika -> {
                _state.value = state.value.copy(
                    slika = event.context.openFileInput(state.value.uriSlikeProizvoda).readBytes()
                )
            }
            is ProizvodViewEvent.DeleteImage -> {
                event.context.deleteFile(state.value.uriSlikeProizvoda)
                _state.value = state.value.copy(
                    uriSlikeProizvoda = "",
                    slika = null
                )
                viewModelScope.launch {
                    proizvodViewUseCases.deleteProizvodImage(state.value.idProizvoda)
                }
            }
            is ProizvodViewEvent.NewSlika -> {
                if (state.value.slika != null) {
                    event.context.deleteFile(state.value.uriSlikeProizvoda)
                }
                val uriSlike = "${event.image.hashCode()}.jpg"
                _state.value = state.value.copy(
                    slika = event.image,
                    uriSlikeProizvoda = uriSlike
                )
                viewModelScope.launch {
                    proizvodViewUseCases.updateSlikaProizvoda(state.value.idProizvoda, uriSlike)
                }
                event.context.openFileOutput(state.value.uriSlikeProizvoda, Context.MODE_PRIVATE).use {
                    it.write(event.image)
                }

            }
        }
    }

    sealed class UiEvent {
        data class DeletedProizvod(val idProizvoda: Int): UiEvent()
        data class ShowToast(val message: String): UiEvent()
        object LoadSlika: UiEvent()
    }
}