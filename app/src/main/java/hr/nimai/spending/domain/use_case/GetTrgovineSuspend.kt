package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.repository.TrgovinaRepository

class GetTrgovineSuspend(
    private val trgovinaRepository: TrgovinaRepository
) {

    suspend operator fun invoke(): List<Trgovina> {
        return trgovinaRepository.getTrgovineSuspend()
    }
}