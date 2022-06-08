package hr.nimai.spending.data.data_source

import androidx.room.*
import hr.nimai.spending.domain.model.Kupnja
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder
import kotlinx.coroutines.flow.Flow

@Dao
interface KupnjaDao {
    @Query("SELECT * FROM kupnja")
    fun getAll(): Flow<List<Kupnja>>

    @Query("SELECT * FROM kupnja WHERE id_racuna = :id_racuna AND id_proizvoda = :id_proizvoda")
    suspend fun getKupnjaByIds(id_racuna: Int, id_proizvoda: Int): Kupnja?

    @Query("SELECT * FROM kupnja WHERE id_proizvoda = :id_proizvoda")
    suspend fun getKupnjeByIdProizvoda(id_proizvoda: Int): List<Kupnja>

    @Query("SELECT * FROM kupnja WHERE id_racuna = :id_racuna")
    suspend fun getKupnjeByIdRacuna(id_racuna: Int): List<Kupnja>

    @Query("SELECT naziv_proizvoda, skraceni_naziv_proizvoda, kolicina, cijena, kupnja.id_proizvoda as id_proizvoda, barkod " +
            "FROM kupnja JOIN proizvod ON kupnja.id_proizvoda = proizvod.id_proizvoda " +
            "WHERE id_racuna = :id_racuna")
    fun getKupnjeProizvodaRacuna(id_racuna: Int): Flow<List<KupnjaProizvodaHolder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKupnja(kupnja: Kupnja)

    @Update
    suspend fun updateKupnja(kupnja: Kupnja)

    @Delete
    suspend fun deleteKupnja(kupnja: Kupnja)
}