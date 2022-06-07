package hr.nimai.spending.presentation.racuni

import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.util.OrderType
import hr.nimai.spending.domain.util.RacunOrder
import hr.nimai.spending.domain.util.RacunTrgovina

data class RacuniState(
    val racuni: List<RacunTrgovina> = emptyList(),
    val racunOrder: RacunOrder = RacunOrder.Datum(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
