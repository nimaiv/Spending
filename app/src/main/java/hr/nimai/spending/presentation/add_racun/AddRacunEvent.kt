package hr.nimai.spending.presentation.add_racun

import androidx.compose.ui.focus.FocusState

sealed class AddRacunEvent {
    data class EnteredBrojRacuna(val value: String) : AddRacunEvent()
    data class ChangeBrojRacunaFocus(val focusState: FocusState): AddRacunEvent()
    data class EnteredTrgovina(val value: String) : AddRacunEvent()
    data class ChangeTrgovinaFocus(val focusState: FocusState): AddRacunEvent()
    data class EnteredUkupanIznos(val value: String) : AddRacunEvent()
    data class ChangeUkupanIznosFocus(val focusState: FocusState): AddRacunEvent()
    data class EnteredDatumRacuna(val value: String) : AddRacunEvent()
    data class ChangeDatumRacunaFocus(val focusState: FocusState): AddRacunEvent()
    object SaveRacun: AddRacunEvent()
}
