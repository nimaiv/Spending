package hr.nimai.spending.data.repository

import hr.nimai.spending.data.data_source.ProizvodDao
import hr.nimai.spending.domain.model.Proizvod
import hr.nimai.spending.domain.repository.ProizvodRepository
import kotlinx.coroutines.flow.Flow

class ProizvodRepositoryImpl(
    private val dao: ProizvodDao
) : ProizvodRepository {

    override fun getAll(): Flow<List<Proizvod>> {
        return dao.getAll()
    }

    override suspend fun getProizvodById(id: Int): Proizvod? {
        return dao.getProizvodById(id)
    }

    override suspend fun insertProizvod(proizvod: Proizvod) {
        return dao.insertProizvod(proizvod)
    }

    override suspend fun updateProizvod(proizvod: Proizvod) {
        return dao.updateProizvod(proizvod)
    }

    override suspend fun deleteProizvod(proizvod: Proizvod) {
        return dao.deleteProizvod(proizvod)
    }
}