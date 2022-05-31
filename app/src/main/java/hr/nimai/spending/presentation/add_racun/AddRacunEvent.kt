package hr.nimai.spending.presentation.add_racun

import androidx.compose.ui.focus.FocusState
import hr.nimai.spending.domain.util.ProizvodKupnjaHolder

sealed class AddRacunEvent {
    data class EnteredBrojRacuna(val value: String) : AddRacunEvent()
    data class EnteredTrgovina(val value: String) : AddRacunEvent()
    data class EnteredUkupanIznos(val value: String) : AddRacunEvent()
    data class EnteredDatumRacuna(val value: String) : AddRacunEvent()
    data class EnteredNazivProizvoda(val value: String) : AddRacunEvent()
    data class EnteredSkraceniNazivProizvoda(val value: String) : AddRacunEvent()
    data class EnteredCijenaProizvoda(val value: String) : AddRacunEvent()
    data class EnteredKolicinaProizvoda(val value: String) : AddRacunEvent()
    object EditProizvodValues : AddRacunEvent()
    data class OpenDialog(val proizvod: ProizvodKupnjaHolder, val id: Int): AddRacunEvent()
    object DismissDialog: AddRacunEvent()
    object SaveRacun: AddRacunEvent()
}
