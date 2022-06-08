package hr.nimai.spending.presentation.racun_proizvodi

import android.content.Context
import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder

sealed class RacunProizvodiEvent {
    data class LoadSlika(val context: Context) : RacunProizvodiEvent()
    data class EnteredBrojRacuna(val value: String) : RacunProizvodiEvent()
    data class EnteredDatumRacuna(val value: String) : RacunProizvodiEvent()
    data class EnteredUkupanIznosRacuna(val value: String) : RacunProizvodiEvent()
    data class SelectTrgovina(val trgovina: Trgovina) : RacunProizvodiEvent()
    data class DeleteRacun(val context: Context) : RacunProizvodiEvent()
    data class ShowEditKupnjaDialog(val kupnjaProizvoda: KupnjaProizvodaHolder) : RacunProizvodiEvent()
    data class EnteredNazivProizvoda(val value: String) : RacunProizvodiEvent()
    data class EnteredSkraceniNazivProizvoda(val value: String) : RacunProizvodiEvent()
    data class EnteredCijenaProizvoda(val value: String) : RacunProizvodiEvent()
    data class EnteredKolicinaProizvoda(val value: String) : RacunProizvodiEvent()
    data class GetDataWithBarcode(val barcode: String) : RacunProizvodiEvent()
    data class AddExistingProizvod(val idProizvoda: Int) : RacunProizvodiEvent()
    data class SaveKupnja(val context: Context) : RacunProizvodiEvent()
    object SaveRacun : RacunProizvodiEvent()
    object ShowOCRTextDialog : RacunProizvodiEvent()
    object ShowEditRacunDialog : RacunProizvodiEvent()
    object DismissDialog : RacunProizvodiEvent()
    object ShowNewKupnjaDialog : RacunProizvodiEvent()
    object ScanBarcode : RacunProizvodiEvent()
    object DeleteKupnja : RacunProizvodiEvent()


}
