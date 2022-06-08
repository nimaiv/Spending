package hr.nimai.spending.presentation.select_spending

sealed class SelectSpendingEvent {
    object SelectProizvodi: SelectSpendingEvent()
    object SelectTrgovine: SelectSpendingEvent()
    object SelectTipoviProizvoda: SelectSpendingEvent()
}
