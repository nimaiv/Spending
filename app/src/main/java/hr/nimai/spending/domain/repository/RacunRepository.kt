package hr.nimai.spending.domain.repository

import hr.nimai.spending.domain.model.Racun
import kotlinx.coroutines.flow.Flow

interface RacunRepository {

    fun getAll(): Flow<List<Racun>>

    suspend fun getRacunById(id: Int): Racun?

    suspend fun insertRacun(racun: Racun): Long

    suspend fun updateRacun(racun: Racun)

    suspend fun deleteRacun(racun: Racun)
}