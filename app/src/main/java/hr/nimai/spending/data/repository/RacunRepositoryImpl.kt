package hr.nimai.spending.data.repository

import hr.nimai.spending.data.data_source.RacunDao
import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.repository.RacunRepository
import hr.nimai.spending.domain.util.RacunTrgovina
import kotlinx.coroutines.flow.Flow

class RacunRepositoryImpl(
    private val dao: RacunDao
) : RacunRepository {

    override fun getAll(): Flow<List<Racun>> {
        return dao.getAll()
    }

    override fun getAllWithTrgovina(): Flow<List<RacunTrgovina>> {
        return dao.getAllWithTrgovine()
    }

    override suspend fun getRacunById(id: Int): Racun? {
        return dao.getRacunById(id)
    }

    override suspend fun insertRacun(racun: Racun): Long {
        return dao.insertRacun(racun)
    }

    override suspend fun updateRacun(racun: Racun) {
        return dao.updateRacun(racun)
    }

    override suspend fun deleteRacun(racun: Racun) {
        return dao.deleteRacun(racun)
    }

    override suspend fun getDatum(idRacuna: Int): String {
        return dao.getDatum(idRacuna)
    }
}