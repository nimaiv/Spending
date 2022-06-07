package hr.nimai.spending.domain.use_case

data class ProizvodViewUseCases(
    val getProizvod: GetProizvod,
    val getKupnjeProizvoda: GetKupnjeProizvoda,
    val getTipoviProizvoda: GetTipoviProizvodaSuspend,
    val insertProizvod: InsertProizvod,
    val deleteProizvod: DeleteProizvod,
    val getProizvodInfoFromBarcode: GetProizvodInfoFromBarcode,
    val downloadImage: DownloadImage,
    val deleteProizvodImage: DeleteProizvodImage,
    val updateSlikaProizvoda: UpdateSlikaProizvoda,
)
