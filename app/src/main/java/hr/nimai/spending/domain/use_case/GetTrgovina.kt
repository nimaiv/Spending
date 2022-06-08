package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.repository.TrgovinaRepository

class GetTrgovina(
    private val trgovinaRepository: TrgovinaRepository
) {
    suspend operator fun invoke(idTrgovine: Int): String? {
        return trgovinaRepository.getTrgovinaById(idTrgovine)?.naziv_trgovine
    }
}