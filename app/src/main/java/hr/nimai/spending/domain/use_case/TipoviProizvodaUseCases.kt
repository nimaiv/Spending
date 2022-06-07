package hr.nimai.spending.domain.use_case

data class TipoviProizvodaUseCases(
    val getTipoviProizvoda: GetTipoviProizvoda,
    val insertTipProizvoda: InsertTipProizvoda,
    val deleteTipProizvoda: DeleteTipProizvoda
)
