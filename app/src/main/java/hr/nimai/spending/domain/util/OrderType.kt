package hr.nimai.spending.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
