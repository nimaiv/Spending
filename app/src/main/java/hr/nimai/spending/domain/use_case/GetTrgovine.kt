package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.repository.TrgovinaRepository
import kotlinx.coroutines.flow.Flow

class GetTrgovine(
    private val trgovinaRepository: TrgovinaRepository
) {

    operator fun invoke(): Flow<List<Trgovina>> {
        return trgovinaRepository.getAll()
    }
}