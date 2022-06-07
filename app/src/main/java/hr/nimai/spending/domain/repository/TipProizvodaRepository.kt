package hr.nimai.spending.domain.repository

import hr.nimai.spending.domain.model.TipProizvoda
import kotlinx.coroutines.flow.Flow

interface TipProizvodaRepository {

    fun getAll(): Flow<List<TipProizvoda>>

    suspend fun getAllSuspend(): List<TipProizvoda>

    suspend fun getTipProizvodaById(id: Int): TipProizvoda?

    suspend fun insertTipProizvoda(tipProizvoda: TipProizvoda)

    suspend fun updateTipProizvoda(tipProizvoda: TipProizvoda)

    suspend fun deleteTipProizvoda(idTipaProizvoda: Int)
}