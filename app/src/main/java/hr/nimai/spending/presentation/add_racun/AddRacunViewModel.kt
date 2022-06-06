package hr.nimai.spending.presentation.add_racun

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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

    private val _brojRacuna = mutableStateOf(
        RacunTextFieldState(
            label = "Broj računa"
        )
    )
    val brojRacuna: State<RacunTextFieldState> = _brojRacuna

    private val _idTrgovine = mutableStateOf(
        RacunTextFieldState(
            label = "Trgovina"
        )
    )
    val idTrgovine: State<RacunTextFieldState> = _idTrgovine

    private val _ukupanIznos = mutableStateOf(
        RacunTextFieldState(
            label = "Ukupan iznos"
        )
    )
    val ukupanIznos: State<RacunTextFieldState> = _ukupanIznos

    private val _datumRacuna = mutableStateOf(
        RacunTextFieldState(
            label = "Datum računa"
        )
    )
    val datumRacuna: State<RacunTextFieldState> = _datumRacuna

    private val _ocrTekst = mutableStateOf(
        RacunTextFieldState(
            label = "Tekst skeniranog računa"
        )
    )
    val ocrTekst: State<RacunTextFieldState> = _ocrTekst

    private val _proizvodiState = mutableStateOf<List<KupnjaProizvodaHolder>>(emptyList())
    val proizvodiState: State<List<KupnjaProizvodaHolder>> = _proizvodiState

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
        _brojRacuna.value = brojRacuna.value.copy(
            text = racun.broj_racuna
        )
        _ukupanIznos.value = ukupanIznos.value.copy(
            text = racun.ukupan_iznos_racuna.toString()
        )
        _datumRacuna.value = datumRacuna.value.copy(
            text = racun.datum_racuna
        )
        _ocrTekst.value = ocrTekst.value.copy(
            text = racun.ocr_tekst!!
        )

        viewModelScope.launch {
            _proizvodiState.value = addRacunUseCases.extractProductInfoFromOCR(ocrText)
        }
    }

    fun onEvent(event: AddRacunEvent) {
        when (event) {
            is AddRacunEvent.EnteredBrojRacuna -> {
                _brojRacuna.value = brojRacuna.value.copy(
                    text = event.value
                )
            }
            is AddRacunEvent.EnteredDatumRacuna -> {
                _datumRacuna.value = datumRacuna.value.copy(
                    text = event.value
                )
            }
            is AddRacunEvent.EnteredTrgovina -> {
                _idTrgovine.value = idTrgovine.value.copy(
                    text = event.value
                )
            }
            is AddRacunEvent.EnteredUkupanIznos -> {
                _ukupanIznos.value = ukupanIznos.value.copy(
                    text = event.value
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
                    uriSlike = event.proizvod.uriSlike?: ""
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
                        isNazivEmptyError = false
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
                        isSkraceniNazivEmptyError = false
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
                    val proizvodi = proizvodiState.value.toMutableList()
                    val proizvod = KupnjaProizvodaHolder(
                        naziv_proizvoda = dialogState.value.nazivProizvoda,
                        skraceni_naziv_proizvoda = dialogState.value.skraceniNazivProizvoda,
                        cijena = dialogState.value.cijenaProizvoda.toDouble(),
                        kolicina = dialogState.value.kolicinaProizvoda.toInt(),
                        barkod = dialogState.value.barkod,
                        uriSlike = dialogState.value.uriSlike,
                        slika = dialogState.value.slika
                    )
                    if (dialogState.value.isNew) {
                        proizvodi.add(proizvod)
                    } else {
                        proizvodi[dialogState.value.id!!] = proizvod
                    }
                    _proizvodiState.value = proizvodi
                    _dialogState.value = dialogState.value.copy(
                        isDialogOpen = false
                    )
                }
            }
            is AddRacunEvent.SaveRacun -> {
                viewModelScope.launch {
                    try {
                        val idRacuna = addRacunUseCases.insertRacun(
                            Racun(
                                id_racuna = 0,
                                broj_racuna = brojRacuna.value.text,
                                id_trgovine = idTrgovine.value.text.toIntOrNull(),
                                ukupan_iznos_racuna = ukupanIznos.value.text.toDouble(),
                                datum_racuna = datumRacuna.value.text,
                                ocr_tekst = ocrTekst.value.text,
                            )
                        )
                        addRacunUseCases.insertProizvodiKupnja(
                            proizvodi = proizvodiState.value,
                            idRacuna = idRacuna
                        )
                        proizvodiState.value.forEach { proizvod ->
                            if (proizvod.slika != null) {
                                val context = event.context
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
                    val proizvodi  = proizvodiState.value.toMutableList()
                    proizvodi.add(KupnjaProizvodaHolder(
                        naziv_proizvoda = proizvod.naziv_proizvoda,
                        skraceni_naziv_proizvoda = proizvod.skraceni_naziv_proizvoda,
                        id_proizvoda = proizvod.id_proizvoda,
                        kolicina = 1,
                        cijena = 0.00,
                        barkod = proizvod.barkod,
                        uriSlike = proizvod.uri_slike
                    ))
                    _proizvodiState.value = proizvodi
                }

            }
            is AddRacunEvent.DeleteProizvod -> {
                val proizvodi = proizvodiState.value.toMutableList()
                proizvodi.removeAt(event.index)
                _proizvodiState.value = proizvodi
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
                        addRacunUseCases.downdloadImage(proizvod.images[0]) { image ->
                            _dialogState.value = dialogState.value.copy(
                                barkod = event.barcode,
                                nazivProizvoda = proizvod.title,
                                uriSlike = "${event.barcode}.jpg",
                                slika = image
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
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class ShowToast(val message: String): UiEvent()
        object SaveRacun : UiEvent()
        object ScanBarcode: UiEvent()
    }

}