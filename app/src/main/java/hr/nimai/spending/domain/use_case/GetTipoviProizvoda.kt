package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.TipProizvoda
import hr.nimai.spending.domain.repository.TipProizvodaRepository
import kotlinx.coroutines.flow.Flow

class GetTipoviProizvoda(
    private val tipProizvodaRepository: TipProizvodaRepository
) {

    operator fun invoke(): Flow<List<TipProizvoda>> {
        return tipProizvodaRepository.getAll()
    }
}