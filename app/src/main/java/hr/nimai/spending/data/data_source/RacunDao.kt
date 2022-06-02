package hr.nimai.spending.data.data_source

import androidx.room.*
import hr.nimai.spending.domain.model.Racun
import kotlinx.coroutines.flow.Flow

@Dao
interface RacunDao {
    @Query("SELECT * FROM racun")
    fun getAll(): Flow<List<Racun>>

    @Query("SELECT * FROM racun WHERE id_racuna = :id")
    suspend fun getRacunById(id: Int): Racun?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRacun(racun: Racun): Long

    @Update
    suspend fun updateRacun(racun: Racun)

    @Delete
    suspend fun deleteRacun(racun: Racun)
}