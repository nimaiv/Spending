package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Kupnja
import hr.nimai.spending.domain.repository.KupnjaRepository
import hr.nimai.spending.domain.repository.RacunRepository
import hr.nimai.spending.domain.util.KupnjaDatum

class GetKupnjeProizvoda(
    private val kupnjaRepository: KupnjaRepository,
    private val racunRepository: RacunRepository
) {

    suspend operator fun invoke(idProizvoda: Int): List<KupnjaDatum> {
        val kupnje = kupnjaRepository.getKupnjeByIdProizvoda(idProizvoda)
        return kupnje.map { kupnja ->
            KupnjaDatum(kupnja, racunRepository.getDatum(kupnja.id_racuna))
        }
    }
}