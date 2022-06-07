package hr.nimai.spending.domain.repository

import hr.nimai.spending.domain.model.Trgovina
import kotlinx.coroutines.flow.Flow

interface TrgovinaRepository {

    fun getAll(): Flow<List<Trgovina>>

    suspend fun getTrgovineSuspend(): List<Trgovina>

    suspend fun getTrgovinaById(id: Int): Trgovina?

    suspend fun insertTrgovina(trgovina: Trgovina)

    suspend fun updateTrgovina(trgovina: Trgovina)

    suspend fun deleteTrgovina(trgovina: Trgovina)
}