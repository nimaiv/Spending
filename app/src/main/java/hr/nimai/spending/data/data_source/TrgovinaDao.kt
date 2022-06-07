package hr.nimai.spending.data.data_source

import androidx.room.*
import hr.nimai.spending.domain.model.Trgovina
import kotlinx.coroutines.flow.Flow

@Dao
interface TrgovinaDao {

    @Query("SELECT * FROM trgovina")
    fun getAll(): Flow<List<Trgovina>>

    @Query("SELECT * FROM trgovina")
    suspend fun getTrgovineSuspend(): List<Trgovina>

    @Query("SELECT * FROM trgovina WHERE id_trgovine = :id")
    suspend fun getTrgovinaById(id: Int): Trgovina?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrgovina(trgovina: Trgovina)

    @Update
    suspend fun updateTrgovina(trgovina: Trgovina)

    @Delete
    suspend fun deleteTrgovina(trgovina: Trgovina)
}