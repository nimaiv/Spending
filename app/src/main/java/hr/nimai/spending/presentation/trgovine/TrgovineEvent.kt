package hr.nimai.spending.presentation.trgovine

import hr.nimai.spending.domain.model.Trgovina

sealed class TrgovineEvent {
    data class EnteredNazivTrgovine(val value: String): TrgovineEvent()
    data class EnteredAdresaTrgovine(val value: String): TrgovineEvent()
    data class EditTrgovinaDialog(val trgovina: Trgovina): TrgovineEvent()
    object DeleteTrgovina : TrgovineEvent()
    object AddTrgovinaDialog: TrgovineEvent()
    object SaveTrgovina: TrgovineEvent()
    object DismissDialog: TrgovineEvent()
}