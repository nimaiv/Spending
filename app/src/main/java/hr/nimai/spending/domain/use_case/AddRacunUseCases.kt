package hr.nimai.spending.domain.use_case

data class AddRacunUseCases(
    val insertRacun: InsertRacun,
    val readOCRToRacun: ReadOCRToRacun,
    val extractProductInfoFromOCR: ExtractProductInfoFromOCR,
)
