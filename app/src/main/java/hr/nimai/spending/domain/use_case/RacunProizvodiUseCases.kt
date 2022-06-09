package hr.nimai.spending.domain.use_case

data class RacunProizvodiUseCases(
    val getRacun: GetRacun,
    val getKupnjeProizvodaRacuna: GetKupnjeProizvodaRacuna,
    val getTrgovina: GetTrgovina,
    val getTrgovineSuspend: GetTrgovineSuspend,
    val updateRacun: UpdateRacun,
    val deleteRacun: DeleteRacun,
    val insertKupnjaProizvoda: InsertKupnjaProizvoda,
    val getProizvodInfoFromBarcode: GetProizvodInfoFromBarcode,
    val downloadImage: DownloadImage,
    val getProizvod: GetProizvod,
    val deleteKupnja: DeleteKupnja,
    val updateKupnjaProizvoda: UpdateKupnjaProizvoda,
)