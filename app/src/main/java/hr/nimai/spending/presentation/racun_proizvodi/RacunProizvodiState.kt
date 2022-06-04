package hr.nimai.spending.presentation.racun_proizvodi

import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder

data class RacunProizvodiState(
    val racun: Racun? = null,
    val kupnjeProizvoda: List<KupnjaProizvodaHolder> = emptyList(),
    val isEditEnabled: Boolean = false,


)
