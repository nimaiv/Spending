package hr.nimai.spending.presentation.add_racun

import hr.nimai.spending.domain.util.KupnjaProizvodaHolder

sealed class AddRacunEvent {
    data class EnteredBrojRacuna(val value: String) : AddRacunEvent()
    data class EnteredTrgovina(val value: String) : AddRacunEvent()
    data class EnteredUkupanIznos(val value: String) : AddRacunEvent()
    data class EnteredDatumRacuna(val value: String) : AddRacunEvent()
    data class EnteredNazivProizvoda(val value: String) : AddRacunEvent()
    data class EnteredSkraceniNazivProizvoda(val value: String) : AddRacunEvent()
    data class EnteredCijenaProizvoda(val value: String) : AddRacunEvent()
    data class EnteredKolicinaProizvoda(val value: String) : AddRacunEvent()
    data class OpenDialog(val proizvod: KupnjaProizvodaHolder, val id: Int): AddRacunEvent()
    data class AddExistingProizvod(val idProizvoda: Int): AddRacunEvent()
    data class DeleteProizvod(val index: Int): AddRacunEvent()
    data class GetDataWithBarcode(val barcode: String): AddRacunEvent()
    object ScanBarcode: AddRacunEvent()
    object EditProizvodValues: AddRacunEvent()
    object AddNewProizvodDialog: AddRacunEvent()
    object DismissDialog: AddRacunEvent()
    object SaveRacun: AddRacunEvent()
}
