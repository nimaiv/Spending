package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.repository.RacunRepository

class DeleteRacun(
    private val repository: RacunRepository
) {

    suspend operator fun invoke(racun: Racun) {
        repository.deleteRacun(racun)
    }
}