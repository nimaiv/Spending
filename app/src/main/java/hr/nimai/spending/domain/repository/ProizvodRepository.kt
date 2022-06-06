package hr.nimai.spending.domain.repository

import hr.nimai.spending.domain.model.Proizvod
import kotlinx.coroutines.flow.Flow

interface ProizvodRepository {

    fun getAll(): Flow<List<Proizvod>>

    suspend fun getAllSuspend(): List<Proizvod>

    suspend fun getProizvodById(id: Int): Proizvod?

    suspend fun insertProizvod(proizvod: Proizvod): Long

    suspend fun updateProizvod(proizvod: Proizvod)

    suspend fun deleteProizvod(proizvod: Proizvod)

    suspend fun deleteSlikuProizvoda(idProizvoda: Int)

    suspend fun updateSlikaProizvoda(idProizvoda: Int, uriSlikeProizvoda: String)
}