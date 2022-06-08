package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.repository.RacunRepository

class UpdateRacun(
    private val racunRepository: RacunRepository
) {
    suspend operator fun invoke(racun: Racun) {
        racunRepository.updateRacun(racun)
    }
}
