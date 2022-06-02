package hr.nimai.spending.presentation.proizvod_view

import hr.nimai.spending.domain.model.TipProizvoda

sealed class ProizvodViewEvent {
    data class OnDropdownItemSelect(val tipProizvoda: TipProizvoda): ProizvodViewEvent()
}
