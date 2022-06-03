package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.repository.KupnjaRepository
import hr.nimai.spending.domain.repository.RacunRepository
import hr.nimai.spending.domain.util.KupnjaDatum
import java.text.SimpleDateFormat
import java.util.*

class GetKupnjeProizvoda(
    private val kupnjaRepository: KupnjaRepository,
    private val racunRepository: RacunRepository
) {

    suspend operator fun invoke(idProizvoda: Int): List<KupnjaDatum> {
        val kupnje = kupnjaRepository.getKupnjeByIdProizvoda(idProizvoda)
        val sdtF = SimpleDateFormat("dd.MM.yyyy.", Locale.GERMANY)
        return kupnje.map { kupnja ->
            KupnjaDatum(kupnja, racunRepository.getDatum(kupnja.id_racuna))
        }.sortedByDescending { sdtF.parse(it.datum) }
    }
}