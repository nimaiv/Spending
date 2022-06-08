package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Kupnja
import hr.nimai.spending.domain.repository.KupnjaRepository

class DeleteKupnja(
    private val kupnjaRepository: KupnjaRepository
) {
    suspend operator fun invoke(kupnja: Kupnja) {
        kupnjaRepository.deleteKupnja(kupnja)
    }
}