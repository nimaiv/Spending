package hr.nimai.spending.presentation.trgovine

sealed class TrgovineEvent {
    data class EnteredNazivTrgovine(val value: String): TrgovineEvent()
    data class EnteredAdresaTrgovine(val value: String): TrgovineEvent()
    object AddTrgovnaDialog: TrgovineEvent()
    object SaveTrgovina: TrgovineEvent()
    object DismissDialog: TrgovineEvent()
}