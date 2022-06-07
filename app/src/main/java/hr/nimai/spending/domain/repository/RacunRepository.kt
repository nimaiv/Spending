package hr.nimai.spending.domain.repository

import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.util.RacunTrgovina
import kotlinx.coroutines.flow.Flow

interface RacunRepository {

    fun getAll(): Flow<List<Racun>>

    fun getAllWithTrgovina(): Flow<List<RacunTrgovina>>

    suspend fun getRacunById(id: Int): Racun?

    suspend fun insertRacun(racun: Racun): Long

    suspend fun updateRacun(racun: Racun)

    suspend fun deleteRacun(racun: Racun)

    suspend fun getDatum(idRacuna: Int): String
}