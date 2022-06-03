package hr.nimai.spending.presentation.proizvod_view

import hr.nimai.spending.domain.model.TipProizvoda

sealed class ProizvodViewEvent {
    data class OnDropdownItemSelect(val tipProizvoda: TipProizvoda): ProizvodViewEvent()
    data class EnteredNazivProizvoda(val value: String): ProizvodViewEvent()
    data class EnteredSkraceniNazivProizvoda(val value: String): ProizvodViewEvent()
    data class EnteredBarkod(val value: String): ProizvodViewEvent()
    object ToggleEdit: ProizvodViewEvent()
}
