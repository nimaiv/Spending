package hr.nimai.spending.presentation.racun_proizvodi

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.model.InvalidRacunException
import hr.nimai.spending.domain.model.Kupnja
import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.use_case.RacunProizvodiUseCases
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RacunProizvodiViewModel @Inject constructor(
    private val racunProizvodiUseCases: RacunProizvodiUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = mutableStateOf(RacunProizvodiState())
    val state: State<RacunProizvodiState> = _state

    private val _editRacunDialogState = mutableStateOf(EditRacunDialogState())
    val editRacunDialogState: State<EditRacunDialogState> = _editRacunDialogState

    private val _editKupnjaDialogState = mutableStateOf(EditKupnjaDialogState())
    val editKupnjaDialogState: State<EditKupnjaDialogState> = _editKupnjaDialogState

    private var getKupnjeProizvodaJob: Job? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val idRacuna = savedStateHandle.get<Int>("idRacuna")
        _state.value = state.value.copy(
            idRacuna = idRacuna!!
        )
        viewModelScope.launch {
            _state.value = state.value.copy(
                racun = racunProizvodiUseCases.getRacun(idRacuna)
            )
            if (state.value.racun?.id_trgovine != null) {
                _state.value = state.value.copy(
                    nazivTrgovine = racunProizvodiUseCases.getTrgovina(state.value.racun?.id_trgovine!!)?:"-"
                )
            }
            if (!state.value.racun?.uri_slike.isNullOrBlank()) {
                _eventFlow.emit(UiEvent.LoadSlika)
            }
            _editRacunDialogState.value = editRacunDialogState.value.copy(
                trgovine = racunProizvodiUseCases.getTrgovineSuspend()
            )
            getKupnjeProizvoda()
        }

    }

    fun onEvent(event: RacunProizvodiEvent) {
        when (event) {
            is RacunProizvodiEvent.LoadSlika -> {
                _state.value = state.value.copy(
                    slika = event.context.openFileInput(state.value.racun?.uri_slike).readBytes()
                )
            }
            is RacunProizvodiEvent.ShowOCRTextDialog -> {
                _state.value = state.value.copy(
                    isOCRTextDialogShown = true
                )
            }
            is RacunProizvodiEvent.DismissDialog -> {
                _state.value = state.value.copy(
                    isOCRTextDialogShown = false,
                    isEditKupnjaDialogShown = false,
                    isEditRacunDialogShown = false,
                )
            }
            is RacunProizvodiEvent.EnteredBrojRacuna -> {
                if (event.value.isNotBlank()) {
                    _editRacunDialogState.value = editRacunDialogState.value.copy(
                        brojRacuna = event.value,
                        isBrojRacunaError = false,
                    )
                    if (!editRacunDialogState.value.isDatumRacunaError &&
                        !editRacunDialogState.value.isBrojRacunaError &&
                        !editRacunDialogState.value.isUkupanIznosError) {
                        _editRacunDialogState.value = editRacunDialogState.value.copy(
                            showErrorMessage = false
                        )
                    }
                } else {
                    _editRacunDialogState.value = editRacunDialogState.value.copy(
                        brojRacuna = event.value,
                        isBrojRacunaError = true
                    )
                }
            }
            is RacunProizvodiEvent.EnteredDatumRacuna -> {
                if (event.value.matches(Regex("""\d\d\.\d\d\.\d\d\d\d\."""))) {
                    _editRacunDialogState.value = editRacunDialogState.value.copy(
                        datumRacuna = event.value,
                        isDatumRacunaError = false,
                    )
                    if (!editRacunDialogState.value.isDatumRacunaError &&
                        !editRacunDialogState.value.isBrojRacunaError &&
                        !editRacunDialogState.value.isUkupanIznosError) {
                        _editRacunDialogState.value = editRacunDialogState.value.copy(
                            showErrorMessage = false
                        )
                    }
                } else {
                    _editRacunDialogState.value = editRacunDialogState.value.copy(
                        datumRacuna = event.value,
                        isDatumRacunaError = true
                    )
                }
            }
            is RacunProizvodiEvent.EnteredUkupanIznosRacuna -> {
                if (event.value.toDoubleOrNull() != null && (event.value.toDoubleOrNull()
                        ?: 0.0) > 0.0
                ) {
                    _editRacunDialogState.value = editRacunDialogState.value.copy(
                        ukupanIznosRacuna = event.value,
                        isUkupanIznosError = false,
                    )
                    if (!editRacunDialogState.value.isDatumRacunaError &&
                        !editRacunDialogState.value.isBrojRacunaError &&
                        !editRacunDialogState.value.isUkupanIznosError) {
                        _editRacunDialogState.value = editRacunDialogState.value.copy(
                            showErrorMessage = false
                        )
                    }
                } else {
                    _editRacunDialogState.value = editRacunDialogState.value.copy(
                        ukupanIznosRacuna = event.value,
                        isUkupanIznosError = true
                    )
                }
            }
            is RacunProizvodiEvent.SelectTrgovina -> {
                _editRacunDialogState.value = editRacunDialogState.value.copy(
                    idTrgovine = event.trgovina.id_trgovine,
                    nazivTrgovine = event.trgovina.naziv_trgovine
                )
            }
            is RacunProizvodiEvent.SaveRacun -> {
                viewModelScope.launch {
                    try {
                        if (!editRacunDialogState.value.isDatumRacunaError &&
                            !editRacunDialogState.value.isBrojRacunaError &&
                            !editRacunDialogState.value.isUkupanIznosError) {
                            val racun = Racun(
                                id_racuna = state.value.racun?.id_racuna!!,
                                broj_racuna = editRacunDialogState.value.brojRacuna,
                                id_trgovine = editRacunDialogState.value.idTrgovine,
                                ukupan_iznos_racuna = editRacunDialogState.value.ukupanIznosRacuna.toDouble(),
                                datum_racuna = editRacunDialogState.value.datumRacuna,
                                uri_slike = state.value.racun?.uri_slike,
                                ocr_tekst = state.value.racun?.ocr_tekst
                            )
                            racunProizvodiUseCases.updateRacun(racun)
                            _state.value = state.value.copy(
                                racun = racun,
                                isEditRacunDialogShown = false,
                                nazivTrgovine = editRacunDialogState.value.nazivTrgovine
                            )
                        } else {
                            _editRacunDialogState.value = editRacunDialogState.value.copy(
                                showErrorMessage = true
                            )
                        }
                        _eventFlow.emit(
                            UiEvent.ShowToast(
                                message = "Spremljeno"
                            )
                        )
                    } catch (e: InvalidRacunException) {
                        _eventFlow.emit(
                            UiEvent.ShowToast(
                                message = e.message ?: "Spremanje neuspješno"
                            )
                        )
                    }
                }
            }
            is RacunProizvodiEvent.ShowEditRacunDialog -> {
                _state.value = state.value.copy(
                    isEditRacunDialogShown = true
                )
                _editRacunDialogState.value = editRacunDialogState.value.copy(
                    brojRacuna = state.value.racun?.broj_racuna?:"",
                    datumRacuna = state.value.racun?.datum_racuna?:"",
                    ukupanIznosRacuna = state.value.racun?.ukupan_iznos_racuna.toString(),
                    idTrgovine = state.value.racun?.id_trgovine,
                    nazivTrgovine = state.value.nazivTrgovine,
                )
            }
            is RacunProizvodiEvent.DeleteRacun -> {
                viewModelScope.launch {
                    racunProizvodiUseCases.deleteRacun(state.value.racun!!)
                    if (state.value.racun?.uri_slike != null) {
                        event.context.deleteFile(state.value.racun?.uri_slike!!)
                    }
                    _eventFlow.emit(UiEvent.DeleteRacun("Račun je obrisan"))
                }
            }
            is RacunProizvodiEvent.EnteredCijenaProizvoda -> {
                if (event.value.toDoubleOrNull() != null && (event.value.toDoubleOrNull()
                        ?: 0.0) > 0.0) {
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        cijenaProizvoda = event.value,
                        isCijenaError = false
                    )
                } else {
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        cijenaProizvoda = event.value,
                        isCijenaError = true
                    )
                }
            }
            is RacunProizvodiEvent.EnteredKolicinaProizvoda -> {
                if (event.value.toIntOrNull() != null && (event.value.toIntOrNull()?: 0) > 0) {
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        kolicinaProizvoda = event.value,
                        isKolicinaError = false
                    )
                } else {
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        kolicinaProizvoda = event.value,
                        isKolicinaError = true
                    )
                }
            }
            is RacunProizvodiEvent.EnteredNazivProizvoda -> {
                if (event.value.isNotBlank()) {
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        nazivProizvoda = event.value,
                        isNazivEmptyError = false
                    )
                } else {
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        nazivProizvoda = event.value,
                        isNazivEmptyError = true
                    )
                }
            }
            is RacunProizvodiEvent.EnteredSkraceniNazivProizvoda -> {
                if (event.value.isNotBlank()) {
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        skraceniNazivProizvoda = event.value,
                        isSkraceniNazivEmptyError = false
                    )
                } else {
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        skraceniNazivProizvoda = event.value,
                        isSkraceniNazivEmptyError = true
                    )
                }
            }
            is RacunProizvodiEvent.SaveKupnja -> {
                if (!editKupnjaDialogState.value.isCijenaError &&
                    !editKupnjaDialogState.value.isKolicinaError &&
                    !editKupnjaDialogState.value.isNazivEmptyError &&
                    !editKupnjaDialogState.value.isSkraceniNazivEmptyError) {

                    try {
                        viewModelScope.launch {
                            if (editKupnjaDialogState.value.isNew) {
                                racunProizvodiUseCases.insertKupnjaProizvoda(
                                    kupnjaProizvoda = KupnjaProizvodaHolder(
                                        id_proizvoda = editKupnjaDialogState.value.idProizvoda?:0,
                                        naziv_proizvoda = editKupnjaDialogState.value.nazivProizvoda,
                                        skraceni_naziv_proizvoda = editKupnjaDialogState.value.skraceniNazivProizvoda,
                                        barkod = editKupnjaDialogState.value.barkod,
                                        cijena = editKupnjaDialogState.value.cijenaProizvoda.toDouble(),
                                        kolicina = editKupnjaDialogState.value.kolicinaProizvoda.toInt(),
                                        uri_slike = editKupnjaDialogState.value.uriSlike,
                                        tip_proizvoda = editKupnjaDialogState.value.idTipaProizvoda
                                    ),
                                    idRacuna = state.value.idRacuna
                                )
                            } else {
                                racunProizvodiUseCases.updateKupnjaProizvoda(
                                    kupnjaProizvoda = KupnjaProizvodaHolder(
                                        id_proizvoda = editKupnjaDialogState.value.idProizvoda?:0,
                                        naziv_proizvoda = editKupnjaDialogState.value.nazivProizvoda,
                                        skraceni_naziv_proizvoda = editKupnjaDialogState.value.skraceniNazivProizvoda,
                                        barkod = editKupnjaDialogState.value.barkod,
                                        cijena = editKupnjaDialogState.value.cijenaProizvoda.toDouble(),
                                        kolicina = editKupnjaDialogState.value.kolicinaProizvoda.toInt(),
                                        uri_slike = editKupnjaDialogState.value.uriSlike,
                                        tip_proizvoda = editKupnjaDialogState.value.idTipaProizvoda
                                    ),
                                    idRacuna = state.value.idRacuna
                                )
                            }

                            _state.value = state.value.copy(
                                isEditKupnjaDialogShown = false
                            )
                            if (editKupnjaDialogState.value.slika != null) {
                                val fileName = "${editKupnjaDialogState.value.barkod}.jpg"
                                event.context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                                    it.write(editKupnjaDialogState.value.slika)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        viewModelScope.launch {
                            _eventFlow.emit(
                                UiEvent.ShowToast(
                                    message = e.message ?: "Spremanje neuspješno"
                                )
                            )
                        }
                    }

                } else {
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        showErrorMessage = true
                    )
                }

            }
            is RacunProizvodiEvent.ScanBarcode -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ScanBarcode)
                }
            }
            is RacunProizvodiEvent.ShowEditKupnjaDialog -> {
                _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                    idProizvoda = event.kupnjaProizvoda.id_proizvoda,
                    nazivProizvoda = event.kupnjaProizvoda.naziv_proizvoda,
                    skraceniNazivProizvoda = event.kupnjaProizvoda.skraceni_naziv_proizvoda,
                    cijenaProizvoda = event.kupnjaProizvoda.cijena.toString(),
                    kolicinaProizvoda = event.kupnjaProizvoda.kolicina.toString(),
                    isNew = false,
                    barkod = event.kupnjaProizvoda.barkod?: "",
                    uriSlike = event.kupnjaProizvoda.uri_slike?: "",
                    isNazivEmptyError = false,
                    isSkraceniNazivEmptyError = false,
                    isKolicinaError = false,
                    isCijenaError = false,
                    idTipaProizvoda = event.kupnjaProizvoda.tip_proizvoda
                )
                _state.value = state.value.copy(
                    isEditKupnjaDialogShown = true
                )
            }
            is RacunProizvodiEvent.ShowNewKupnjaDialog -> {
                _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                    idProizvoda = 0,
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
                    isKolicinaError = false,
                    isCijenaError = false,
                    idTipaProizvoda = null
                )
                _state.value = state.value.copy(
                    isEditKupnjaDialogShown = true
                )
            }
            is RacunProizvodiEvent.AddExistingProizvod -> {
                viewModelScope.launch {
                    val proizvod = racunProizvodiUseCases.getProizvod(event.idProizvoda)
                    _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                        nazivProizvoda = proizvod.naziv_proizvoda,
                        skraceniNazivProizvoda = proizvod.skraceni_naziv_proizvoda,
                        idProizvoda = proizvod.id_proizvoda,
                        barkod = proizvod.barkod?: "",
                        uriSlike = proizvod.uri_slike?:"",
                        idTipaProizvoda = proizvod.tip_proizvoda,
                        cijenaProizvoda = "0.00",
                        kolicinaProizvoda = "1",
                        isSkraceniNazivEmptyError = false,
                        isNazivEmptyError = false,
                    )
                }
            }
            is RacunProizvodiEvent.GetDataWithBarcode -> {
                racunProizvodiUseCases.getProizvodInfoFromBarcode(event.barcode) { proizvodi ->
                    if (proizvodi != null) {
                        val proizvod = proizvodi.products[0]
                        racunProizvodiUseCases.downloadImage(proizvod.images[0]) { image ->
                            _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                                barkod = event.barcode,
                                nazivProizvoda = proizvod.title,
                                uriSlike = "${event.barcode}.jpg",
                                slika = image,
                                isNazivEmptyError = false,
                            )
                        }
                    } else {
                        _editKupnjaDialogState.value = editKupnjaDialogState.value.copy(
                            barkod = event.barcode,
                        )
                        viewModelScope.launch {
                            _eventFlow.emit(UiEvent.ShowToast("Nije pronađen proizvod s barkodom"))
                        }
                    }
                }
            }
            is RacunProizvodiEvent.DeleteKupnja -> {
                viewModelScope.launch {
                    racunProizvodiUseCases.deleteKupnja(Kupnja(
                        id_racuna = state.value.idRacuna,
                        id_proizvoda = editKupnjaDialogState.value.idProizvoda!!,
                    ))
                    _state.value = state.value.copy(
                        isEditKupnjaDialogShown = false
                    )
                }
            }
        }
    }

    private fun getKupnjeProizvoda() {
        getKupnjeProizvodaJob?.cancel()
        getKupnjeProizvodaJob = racunProizvodiUseCases.getKupnjeProizvodaRacuna(state.value.idRacuna)
            .onEach { kupnjeProizvoda ->
                _state.value = state.value.copy(
                    kupnjeProizvoda = kupnjeProizvoda,
                )
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowToast(val message: String): UiEvent()
        data class SaveRacun(val message: String): UiEvent()
        data class DeleteRacun(val message: String): UiEvent()
        object LoadSlika: UiEvent()
        object ScanBarcode: UiEvent()
    }
}