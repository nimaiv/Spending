package hr.nimai.spending.presentation.proizvod_view

import android.content.Context
import hr.nimai.spending.domain.model.TipProizvoda

sealed class ProizvodViewEvent {
    data class OnDropdownItemSelect(val tipProizvoda: TipProizvoda): ProizvodViewEvent()
    data class EnteredNazivProizvoda(val value: String): ProizvodViewEvent()
    data class EnteredSkraceniNazivProizvoda(val value: String): ProizvodViewEvent()
    data class EnteredBarkod(val value: String): ProizvodViewEvent()
    data class SelectTipProizvoda(val value: TipProizvoda): ProizvodViewEvent()
    data class DeleteProizvod(val context: Context): ProizvodViewEvent()
    data class GetDataWithBarcode(val barcode: String, val context: Context): ProizvodViewEvent()
    data class LoadSlika(val context: Context): ProizvodViewEvent()
    data class ToggleEdit(val context: Context): ProizvodViewEvent()
    data class DeleteImage(val context: Context): ProizvodViewEvent()
    data class NewSlika(val image: ByteArray, val context: Context) : ProizvodViewEvent()


}
