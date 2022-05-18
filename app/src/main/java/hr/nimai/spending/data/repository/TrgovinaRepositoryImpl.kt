package hr.nimai.spending.data.repository

import hr.nimai.spending.data.data_source.TrgovinaDao
import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.repository.TrgovinaRepository
import kotlinx.coroutines.flow.Flow

class TrgovinaRepositoryImpl(
    private val dao: TrgovinaDao
) : TrgovinaRepository {

    override fun getAll(): Flow<List<Trgovina>> {
        return dao.getAll()
    }

    override suspend fun getTrgovinaById(id: Int): Trgovina? {
        return dao.getTrgovinaById(id)
    }

    override suspend fun insertTrgovina(trgovina: Trgovina) {
        return dao.insertTrgovina(trgovina)
    }

    override suspend fun updateTrgovina(trgovina: Trgovina) {
        return dao.updateTrgovina(trgovina)
    }

    override suspend fun deleteTrgovina(trgovina: Trgovina) {
        return dao.deleteTrgovina(trgovina)
    }
}