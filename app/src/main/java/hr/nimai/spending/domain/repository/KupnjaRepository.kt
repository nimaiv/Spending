package hr.nimai.spending.domain.repository

import hr.nimai.spending.domain.model.Kupnja
import kotlinx.coroutines.flow.Flow

interface KupnjaRepository {

    fun getAll(): Flow<List<Kupnja>>

    suspend fun getKupnjaById(id_racuna: Int, id_proizvoda: Int): Kupnja?

    suspend fun insertKupnja(kupnja: Kupnja)

    suspend fun updateKupnja(kupnja: Kupnja)

    suspend fun deleteKupnja(kupnja: Kupnja)
}