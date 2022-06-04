package hr.nimai.spending.data.repository

import hr.nimai.spending.data.data_source.KupnjaDao
import hr.nimai.spending.domain.model.Kupnja
import hr.nimai.spending.domain.repository.KupnjaRepository
import kotlinx.coroutines.flow.Flow

class KupnjaRepositoryImpl(
    private val dao: KupnjaDao
) : KupnjaRepository {

    override fun getAll(): Flow<List<Kupnja>> {
        return dao.getAll()
    }

    override suspend fun getKupnjaById(id_racuna: Int, id_proizvoda: Int): Kupnja? {
        return dao.getKupnjaByIds(id_racuna = id_racuna, id_proizvoda = id_proizvoda)
    }

    override suspend fun getKupnjeByIdProizvoda(id_proizvoda: Int): List<Kupnja> {
        return dao.getKupnjeByIdProizvoda(id_proizvoda = id_proizvoda)
    }

    override suspend fun getKupnjeByIdRacuna(id_racuna: Int): List<Kupnja> {
        return dao.getKupnjeByIdRacuna(id_racuna)
    }

    override suspend fun insertKupnja(kupnja: Kupnja) {
        return dao.insertKupnja(kupnja)
    }

    override suspend fun updateKupnja(kupnja: Kupnja) {
        return dao.updateKupnja(kupnja)
    }

    override suspend fun deleteKupnja(kupnja: Kupnja) {
        return dao.deleteKupnja(kupnja)
    }
}