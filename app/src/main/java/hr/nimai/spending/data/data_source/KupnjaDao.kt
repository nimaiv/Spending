package hr.nimai.spending.data.data_source

import androidx.room.*
import hr.nimai.spending.domain.model.Kupnja
import kotlinx.coroutines.flow.Flow

@Dao
interface KupnjaDao {
    @Query("SELECT * FROM kupnja")
    fun getAll(): Flow<List<Kupnja>>

    @Query("SELECT * FROM kupnja WHERE id_racuna = :id_racuna AND id_proizvoda = :id_proizvoda")
    suspend fun getKupnjaByIds(id_racuna: Int, id_proizvoda: Int): Kupnja?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKupnja(kupnja: Kupnja)

    @Update
    suspend fun updateKupnja(kupnja: Kupnja)

    @Delete
    suspend fun deleteKupnja(kupnja: Kupnja)
}