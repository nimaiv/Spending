package hr.nimai.spending.presentation.racuni

import hr.nimai.spending.domain.util.RacunOrder

sealed class RacuniEvent {
    data class Order(val racunOrder: RacunOrder): RacuniEvent()
    object ToggleOrderSection: RacuniEvent()
}
