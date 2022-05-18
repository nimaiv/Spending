package hr.nimai.spending.data.data_source

import androidx.room.*
import hr.nimai.spending.domain.model.TipProizvoda
import kotlinx.coroutines.flow.Flow

@Dao
interface TipProizvodaDao {

    @Query("SELECT * FROM tip_proizvoda")
    fun getAll(): Flow<List<TipProizvoda>>

    @Query("SELECT * FROM tip_proizvoda WHERE id_tipa_proizvoda = :id")
    suspend fun getTipProizvodaById(id: Int): TipProizvoda?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTipProizvoda(tipProizvoda: TipProizvoda)

    @Update
    suspend fun updateTipProizvoda(tipProizvoda: TipProizvoda)

    @Delete
    suspend fun deleteTipProizvoda(tipProizvoda: TipProizvoda)


}