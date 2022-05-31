package hr.nimai.spending.domain.util

data class ProizvodKupnjaHolder(
    val naziv_proizvoda: String = "",
    val skraceni_naziv_proizvoda: String = "",
    val kolicina: Int = 1,
    var cijena: Double = 0.00,
    val id_proizvoda: Int = 0
)
