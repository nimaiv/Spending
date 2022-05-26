package hr.nimai.spending.presentation.add_racun

import androidx.compose.ui.focus.FocusState

sealed class AddRacunEvent {
    data class EnteredBrojRacuna(val value: String) : AddRacunEvent()
    data class EnteredTrgovina(val value: String) : AddRacunEvent()
    data class EnteredUkupanIznos(val value: String) : AddRacunEvent()
    data class EnteredDatumRacuna(val value: String) : AddRacunEvent()
    object SaveRacun: AddRacunEvent()
}
