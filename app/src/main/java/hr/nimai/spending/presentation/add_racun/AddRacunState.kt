package hr.nimai.spending.presentation.add_racun

import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder

data class AddRacunState(
    val brojRacuna: String = "",
    val ukupanIznos: String = "",
    val datumRacuna: String = "",
    val ocrTekst: String = "",
    val idTrgovine: Int? = null,
    val nazivTrgovine: String = "",
    val uriSlike: String = "",
    val slika: ByteArray? = null,
    val proizvodi: List<KupnjaProizvodaHolder> = emptyList(),
    val trgovine: List<Trgovina> = emptyList()


)
