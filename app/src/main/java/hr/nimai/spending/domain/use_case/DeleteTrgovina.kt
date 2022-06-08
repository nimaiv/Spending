package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.repository.TrgovinaRepository

class DeleteTrgovina(
    private val trgovinaRepository: TrgovinaRepository
) {

    suspend operator fun invoke(trgovina: Trgovina) {
        trgovinaRepository.deleteTrgovina(trgovina)
    }
}