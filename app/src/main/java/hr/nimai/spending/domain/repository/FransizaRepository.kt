package hr.nimai.spending.domain.repository

import hr.nimai.spending.domain.model.Fransiza
import kotlinx.coroutines.flow.Flow

interface FransizaRepository {

    fun getAll(): Flow<List<Fransiza>>

    suspend fun getFransizaById(id: Int): Fransiza?

    suspend fun insertFransiza(fransiza: Fransiza)

    suspend fun updateFransiza(fransiza: Fransiza)

    suspend fun deleteFransiza(fransiza: Fransiza)
}