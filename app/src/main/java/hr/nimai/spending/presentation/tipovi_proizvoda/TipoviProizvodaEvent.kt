package hr.nimai.spending.presentation.tipovi_proizvoda

import hr.nimai.spending.domain.model.TipProizvoda

sealed class TipoviProizvodaEvent {
    data class EnteredNaziv(val value: String): TipoviProizvodaEvent()
    data class EditTipProizvodaDialog(val tipProizvoda: TipProizvoda): TipoviProizvodaEvent()
    object AddTipProizvodaDialog: TipoviProizvodaEvent()
    object SaveTipProizvoda: TipoviProizvodaEvent()
    object DismissDialog: TipoviProizvodaEvent()
    object DeleteTipProizvoda: TipoviProizvodaEvent()
}