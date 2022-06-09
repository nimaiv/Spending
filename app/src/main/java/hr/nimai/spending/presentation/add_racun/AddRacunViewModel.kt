package hr.nimai.spending.presentation.add_racun

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.model.InvalidRacunException
import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.use_case.AddRacunUseCases
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRacunViewModel @Inject constructor(
    private val addRacunUseCases: AddRacunUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AddRacunState())
    val state: State<AddRacunState> = _state

    private val _dialogState = mutableStateOf(
        DialogState(
            isDialogOpen = false,
            id = null
        )
    )
    val dialogState: State<DialogState> = _dialogState


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val ocrText = savedStateHandle.get<String>("ocrText")
        val racun = addRacunUseCases.readOCRToRacun(ocrText!!)

        _state.value = state.value.copy(
            slika = savedStateHandle.get<ByteArray>("image"),
            brojRacuna = racun.broj_racuna,
            ukupanIznos = racun.ukupan_iznos_racuna.toString(),
            datumRacuna = racun.datum_racuna,
            ocrTekst = racun.ocr_tekst!!
        )

        viewModelScope.launch {
            _state.value = state.value.copy(
                proizvodi = addRacunUseCases.extractProductInfoFromOCR(ocrText),
                trgovine = addRacunUseCases.getTrgovineSuspend()
            )
        }
    }

    fun onEvent(event: AddRacunEvent) {
        when (event) {
            is AddRacunEvent.EnteredBrojRacuna -> {
                _state.value = state.value.copy(
                    brojRacuna = event.value
                )
            }
            is AddRacunEvent.EnteredDatumRacuna -> {
                _state.value = state.value.copy(
                    datumRacuna = event.value
                )
            }
            is AddRacunEvent.EnteredTrgovina -> {
                _state.value = state.value.copy(
                    nazivTrgovine = event.value
                )
            }
            is AddRacunEvent.EnteredUkupanIznos -> {
                _state.value = state.value.copy(
                    ukupanIznos = event.value
                )
            }
            is AddRacunEvent.OpenDialog -> {
                _dialogState.value = dialogState.value.copy(
                    isDialogOpen = true,
                    id = event.id,
                    nazivProizvoda = event.proizvod.naziv_proizvoda,
                    skraceniNazivProizvoda = event.proizvod.skraceni_naziv_proizvoda,
                    cijenaProizvoda = event.proizvod.cijena.toString(),
                    kolicinaProizvoda = event.proizvod.kolicina.toString(),
                    isNew = false,
                    barkod = event.proizvod.barkod?: "",
                    uriSlike = event.proizvod.uri_slike?: "",
                    isNazivEmptyError = false,
                    isSkraceniNazivEmptyError = false,
                    showErrorMessage = false,
                )
            }
            is AddRacunEvent.DismissDialog -> {
                _dialogState.value = dialogState.value.copy(
                    isDialogOpen = false
                )
            }
            is AddRacunEvent.EnteredCijenaProizvoda -> {
                if (event.value.toDoubleOrNull() == null) {
                    _dialogState.value = _dialogState.value.copy(
                        cijenaProizvoda = event.value,
                        isCijenaError = true
                    )
                } else {
                    _dialogState.value = dialogState.value.copy(
                        cijenaProizvoda = event.value,
                        isCijenaError = false,
                        showErrorMessage = false
                    )
                }
            }
            is AddRacunEvent.EnteredKolicinaProizvoda -> {
                if (event.value.toIntOrNull() == null) {
                    _dialogState.value = _dialogState.value.copy(
                        kolicinaProizvoda = event.value,
                        isKolicinaError = true,
                    )
                } else {
                    _dialogState.value = dialogState.value.copy(
                        kolicinaProizvoda = event.value,
                        isKolicinaError = false,
                        showErrorMessage = false
                    )
                }
            }
            is AddRacunEvent.EnteredNazivProizvoda -> {
                if (event.value.isBlank()) {
                    _dialogState.value = dialogState.value.copy(
                        nazivProizvoda = event.value,
                        isNazivEmptyError = true
                    )
                } else {
                    _dialogState.value = dialogState.value.copy(
                        nazivProizvoda = event.value,
                        isNazivEmptyError = false,
                        showErrorMessage = false
                    )
                }
            }
            is AddRacunEvent.EnteredSkraceniNazivProizvoda -> {
                if (event.value.isBlank()) {
                    _dialogState.value = dialogState.value.copy(
                        skraceniNazivProizvoda = event.value,
                        isSkraceniNazivEmptyError = true
                    )
                } else {
                    _dialogState.value = dialogState.value.copy(
                        skraceniNazivProizvoda = event.value,
                        isSkraceniNazivEmptyError = false,
                        showErrorMessage = false
                    )
                }
            }
            is AddRacunEvent.EditProizvodValues -> {
                if (dialogState.value.isCijenaError ||
                    dialogState.value.isKolicinaError ||
                    dialogState.value.isNazivEmptyError ||
                    dialogState.value.isSkraceniNazivEmptyError) {

                    _dialogState.value = dialogState.value.copy(
                        showErrorMessage = true
                    )
                } else {
                    val proizvodi = state.value.proizvodi.toMutableList()
                    val proizvod = KupnjaProizvodaHolder(
                        naziv_proizvoda = dialogState.value.nazivProizvoda,
                        skraceni_naziv_proizvoda = dialogState.value.skraceniNazivProizvoda,
                        cijena = dialogState.value.cijenaProizvoda.toDouble(),
                        kolicina = dialogState.value.kolicinaProizvoda.toInt(),
                        barkod = dialogState.value.barkod,
                        uri_slike = dialogState.value.uriSlike,
                        slika = dialogState.value.slika
                    )
                    if (dialogState.value.isNew) {
                        proizvodi.add(proizvod)
                    } else {
                        proizvodi[dialogState.value.id!!] = proizvod
                    }
                    _state.value = state.value.copy(
                        proizvodi = proizvodi
                    )
                    _dialogState.value = dialogState.value.copy(
                        isDialogOpen = false
                    )
                }
            }
            is AddRacunEvent.SaveRacun -> {
                viewModelScope.launch {
                    try {
                        val context = event.context
                        var uriSlike = ""
                        if (state.value.slika != null) {
                            uriSlike = "racun${state.value.brojRacuna.hashCode()}.jpg"
                            context.openFileOutput(uriSlike, Context.MODE_PRIVATE).use {
                                it.write(state.value.slika)
                            }
                        }
                        val idRacuna = addRacunUseCases.insertRacun(
                            Racun(
                                id_racuna = 0,
                                broj_racuna = state.value.brojRacuna,
                                id_trgovine = state.value.idTrgovine,
                                ukupan_iznos_racuna = state.value.ukupanIznos.toDouble(),
                                datum_racuna = state.value.datumRacuna,
                                ocr_tekst = state.value.ocrTekst,
                                uri_slike = uriSlike
                            )
                        )
                        addRacunUseCases.insertProizvodiKupnja(
                            proizvodi = state.value.proizvodi,
                            idRacuna = idRacuna
                        )

                        state.value.proizvodi.forEach { proizvod ->
                            if (proizvod.slika != null) {
                                val fileName = "${proizvod.barkod}.jpg"
                                context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                                    it.write(proizvod.slika)
                                }
                            }
                        }

                        _eventFlow.emit(UiEvent.SaveRacun)
                    } catch (e: InvalidRacunException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Spremanje neuspješno"
                            )
                        )
                    }
                }
            }
            is AddRacunEvent.AddNewProizvodDialog -> {
                _dialogState.value = dialogState.value.copy(
                    isDialogOpen = true,
                    id = 0,
                    nazivProizvoda = "",
                    skraceniNazivProizvoda = "",
                    cijenaProizvoda = "0.00",
                    kolicinaProizvoda = "1",
                    isNew = true,
                    barkod = "",
                    uriSlike = "",
                    isSkraceniNazivEmptyError = true,
                    isNazivEmptyError = true,
                    slika = null,
                )
            }
            is AddRacunEvent.AddExistingProizvod -> {
                viewModelScope.launch {
                    val proizvod = addRacunUseCases.getProizvod(event.idProizvoda)
                    val proizvodi  = state.value.proizvodi.toMutableList()
                    proizvodi.add(KupnjaProizvodaHolder(
                        naziv_proizvoda = proizvod.naziv_proizvoda,
                        skraceni_naziv_proizvoda = proizvod.skraceni_naziv_proizvoda,
                        id_proizvoda = proizvod.id_proizvoda,
                        kolicina = 1,
                        cijena = 0.00,
                        barkod = proizvod.barkod,
                        uri_slike = proizvod.uri_slike
                    ))
                    _state.value = state.value.copy(
                        proizvodi = proizvodi
                    )
                }

            }
            is AddRacunEvent.DeleteProizvod -> {
                val proizvodi = state.value.proizvodi.toMutableList()
                proizvodi.removeAt(event.index)
                _state.value = state.value.copy(
                    proizvodi = proizvodi
                )
            }
            is AddRacunEvent.ScanBarcode -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ScanBarcode)
                }
            }
            is AddRacunEvent.GetDataWithBarcode -> {
                addRacunUseCases.getProizvodInfoFromBarcode(event.barcode) { proizvodi ->
                    if (proizvodi != null) {
                        val proizvod = proizvodi.products[0]
                        addRacunUseCases.downloadImage(proizvod.images[0]) { image ->
                            _dialogState.value = dialogState.value.copy(
                                barkod = event.barcode,
                                nazivProizvoda = proizvod.title,
                                uriSlike = "${event.barcode}.jpg",
                                slika = image,
                                isNazivEmptyError = false,
                            )
                        }
                    } else {
                        _dialogState.value = dialogState.value.copy(
                            barkod = event.barcode,
                        )
                        viewModelScope.launch {
                            _eventFlow.emit(UiEvent.ShowToast("Nije pronađen proizvod s barkodom"))
                        }
                    }
                }
            }
            is AddRacunEvent.SelectTrgovina -> {
                _state.value = state.value.copy(
                    idTrgovine = event.trgovina.id_trgovine,
                    nazivTrgovine = event.trgovina.naziv_trgovine
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class ShowToast(val message: String): UiEvent()
        object SaveRacun : UiEvent()
        object ScanBarcode: UiEvent()
    }

}