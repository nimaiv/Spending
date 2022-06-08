package hr.nimai.spending.domain.repository

import hr.nimai.spending.domain.model.Kupnja
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder
import kotlinx.coroutines.flow.Flow

interface KupnjaRepository {

    fun getAll(): Flow<List<Kupnja>>

    suspend fun getKupnjaById(id_racuna: Int, id_proizvoda: Int): Kupnja?

    suspend fun getKupnjeByIdProizvoda(id_proizvoda: Int): List<Kupnja>

    suspend fun getKupnjeByIdRacuna(id_racuna: Int): List<Kupnja>

    fun getKupnjeProizvodaRacuna(id_racuna: Int): Flow<List<KupnjaProizvodaHolder>>

    suspend fun insertKupnja(kupnja: Kupnja)

    suspend fun updateKupnja(kupnja: Kupnja)

    suspend fun deleteKupnja(kupnja: Kupnja)
}