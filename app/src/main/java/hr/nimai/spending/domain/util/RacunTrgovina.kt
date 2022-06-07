package hr.nimai.spending.domain.util


data class RacunTrgovina(
    val id_racuna: Int,
    val broj_racuna: String,
    val datum_racuna: String,
    val ukupan_iznos_racuna: Double,
    val naziv_trgovine: String? = null
)