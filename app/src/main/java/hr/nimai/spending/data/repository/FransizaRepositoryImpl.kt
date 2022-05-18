package hr.nimai.spending.data.repository

import hr.nimai.spending.data.data_source.FransizaDao
import hr.nimai.spending.domain.model.Fransiza
import hr.nimai.spending.domain.repository.FransizaRepository
import kotlinx.coroutines.flow.Flow

class FransizaRepositoryImpl(
    private val dao: FransizaDao
) : FransizaRepository {

    override fun getAll(): Flow<List<Fransiza>> {
        return dao.getAll()
    }

    override suspend fun getFransizaById(id: Int): Fransiza? {
        return dao.getFransizaById(id)
    }

    override suspend fun insertFransiza(fransiza: Fransiza) {
        return dao.insertFransiza(fransiza)
    }

    override suspend fun updateFransiza(fransiza: Fransiza) {
        return dao.updateFransiza(fransiza)
    }

    override suspend fun deleteFransiza(fransiza: Fransiza) {
        return dao.deleteFransiza(fransiza)
    }

}