package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.repository.RacunRepository

class GetRacun(
    private val repository: RacunRepository
) {

    suspend operator fun invoke(id: Int): Racun? {
        return repository.getRacunById(id)
    }
}