package hr.nimai.spending.presentation.add_racun

sealed class AddRacunEvent {
    data class EnteredBrojRacuna(val value: String) : AddRacunEvent()
}
