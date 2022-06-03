package hr.nimai.spending.domain.use_case

data class ProizvodViewUseCases(
    val getProizvod: GetProizvod,
    val getKupnjeProizvoda: GetKupnjeProizvoda,
    val getTipoviProizvoda: GetTipoviProizvoda,
    val insertProizvod: InsertProizvod,
)
