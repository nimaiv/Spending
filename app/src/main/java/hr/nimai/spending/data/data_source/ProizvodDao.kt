package hr.nimai.spending.data.data_source

import androidx.room.*
import hr.nimai.spending.domain.model.Proizvod
import kotlinx.coroutines.flow.Flow

@Dao
interface ProizvodDao {
    @Query("SELECT * FROM proizvod")
    fun getAll(): Flow<List<Proizvod>>

    @Query("SELECT * FROM proizvod WHERE id_proizvoda = :id")
    suspend fun getProizvodById(id: Int): Proizvod?

    @Query("SELECT * FROM proizvod WHERE skraceni_naziv_proizvoda = :skraceniNazivProizvoda")
    suspend fun getProizvodBySkraceniNaziv(skraceniNazivProizvoda: String): Proizvod?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProizvod(proizvod: Proizvod)

    @Update
    suspend fun updateProizvod(proizvod: Proizvod)

    @Delete
    suspend fun deleteProizvod(proizvod: Proizvod)
}