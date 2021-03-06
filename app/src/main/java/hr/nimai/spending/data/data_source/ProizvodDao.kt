package hr.nimai.spending.data.data_source

import androidx.room.*
import hr.nimai.spending.domain.model.Proizvod
import kotlinx.coroutines.flow.Flow

@Dao
interface ProizvodDao {
    @Query("SELECT * FROM proizvod")
    fun getAll(): Flow<List<Proizvod>>

    @Query("SELECT * FROM proizvod")
    suspend fun getAllSuspend(): List<Proizvod>

    @Query("SELECT * FROM proizvod WHERE id_proizvoda = :id")
    suspend fun getProizvodById(id: Int): Proizvod?

    @Query("SELECT * FROM proizvod WHERE skraceni_naziv_proizvoda = :skraceniNazivProizvoda")
    suspend fun getProizvodBySkraceniNaziv(skraceniNazivProizvoda: String): Proizvod?

    @Query("UPDATE proizvod SET uri_slike = null WHERE id_proizvoda = :idProizvoda")
    suspend fun deleteSlikuProizvoda(idProizvoda: Int)

    @Query("UPDATE proizvod SET uri_slike = :uriSlikeProizvoda WHERE id_proizvoda = :idProizvoda")
    suspend fun updateSlikaProizvoda(idProizvoda: Int, uriSlikeProizvoda: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProizvod(proizvod: Proizvod): Long

    @Update
    suspend fun updateProizvod(proizvod: Proizvod)

    @Delete
    suspend fun deleteProizvod(proizvod: Proizvod)
}