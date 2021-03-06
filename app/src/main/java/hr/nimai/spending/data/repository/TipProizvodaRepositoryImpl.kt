package hr.nimai.spending.data.repository

import hr.nimai.spending.data.data_source.TipProizvodaDao
import hr.nimai.spending.domain.model.TipProizvoda
import hr.nimai.spending.domain.repository.TipProizvodaRepository
import kotlinx.coroutines.flow.Flow

class TipProizvodaRepositoryImpl(
    private val dao: TipProizvodaDao
) : TipProizvodaRepository {

    override fun getAll(): Flow<List<TipProizvoda>> {
        return dao.getAll()
    }

    override suspend fun getAllSuspend(): List<TipProizvoda> {
        return dao.getAllSuspend()
    }

    override suspend fun getTipProizvodaById(id: Int): TipProizvoda? {
        return dao.getTipProizvodaById(id)
    }

    override suspend fun insertTipProizvoda(tipProizvoda: TipProizvoda) {
        return dao.insertTipProizvoda(tipProizvoda)
    }

    override suspend fun updateTipProizvoda(tipProizvoda: TipProizvoda) {
        return dao.updateTipProizvoda(tipProizvoda)
    }

    override suspend fun deleteTipProizvoda(idTipaProizvoda: Int) {
        return dao.deleteTipProizvoda(idTipaProizvoda)
    }
}