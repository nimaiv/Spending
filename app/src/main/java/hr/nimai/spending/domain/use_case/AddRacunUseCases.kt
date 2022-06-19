package hr.nimai.spending.domain.use_case

data class AddRacunUseCases(
    val insertRacun: InsertRacun,
    val extractRacunInfoFromOCR: ExtractRacunInfoFromOCR,
    val extractProductInfoFromOCR: ExtractProductInfoFromOCR,
    val insertProizvodiKupnja: InsertProizvodiKupnja,
    val getProizvod: GetProizvod,
    val getProizvodInfoFromBarcode: GetProizvodInfoFromBarcode,
    val downloadImage: DownloadImage,
    val getTrgovineSuspend: GetTrgovineSuspend
)
