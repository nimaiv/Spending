package hr.nimai.spending.domain.util

sealed class RacunOrder(val orderType: OrderType) {
    class Datum(orderType: OrderType): RacunOrder(orderType)
    class Iznos(orderType: OrderType): RacunOrder(orderType)

    fun copy(orderType: OrderType): RacunOrder {
        return when(this) {
            is Datum -> Datum(orderType)
            is Iznos -> Iznos(orderType)
        }
    }
}
