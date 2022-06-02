package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Proizvod
import hr.nimai.spending.domain.repository.ProizvodRepository
import kotlinx.coroutines.flow.Flow

class GetProizvodi(
    private val repository: ProizvodRepository
) {

    operator fun invoke(): Flow<List<Proizvod>> {
        return repository.getAll()
    }
}