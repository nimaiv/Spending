package hr.nimai.spending.presentation.racun_proizvodi

import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder

data class RacunProizvodiState(
    val idRacuna: Int = 0,
    val racun: Racun? = null,
    val nazivTrgovine: String = "",
    val kupnjeProizvoda: List<KupnjaProizvodaHolder> = emptyList(),
    val slika: ByteArray? = null,
    val isOCRTextDialogShown: Boolean = false,
    val isEditRacunDialogShown: Boolean = false,
    val isEditKupnjaDialogShown: Boolean = false,
)
