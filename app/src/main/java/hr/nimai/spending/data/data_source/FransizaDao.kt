package hr.nimai.spending.data.data_source

import androidx.room.*
import hr.nimai.spending.domain.model.Fransiza
import kotlinx.coroutines.flow.Flow

@Dao
interface FransizaDao {

    @Query("SELECT * FROM fransiza")
    fun getAll(): Flow<List<Fransiza>>

    @Query("SELECT * FROM fransiza WHERE id_fransize = :id")
    suspend fun getFransizaById(id: Int): Fransiza?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFransiza(fransiza: Fransiza)

    @Update
    suspend fun updateFransiza(fransiza: Fransiza)

    @Delete
    suspend fun deleteFransiza(fransiza: Fransiza)
}