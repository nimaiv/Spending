package hr.nimai.spending.data.data_source

import androidx.room.*
import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.model.Trgovina
import hr.nimai.spending.domain.util.RacunTrgovina
import kotlinx.coroutines.flow.Flow

@Dao
interface RacunDao {
    @Query("SELECT * FROM racun")
    fun getAll(): Flow<List<Racun>>

    @Query("SELECT id_racuna, broj_racuna, datum_racuna, ukupan_iznos_racuna, naziv_trgovine FROM racun LEFT JOIN trgovina ON racun.id_trgovine = trgovina.id_trgovine ")
    fun getAllWithTrgovine(): Flow<List<RacunTrgovina>>

    @Query("SELECT * FROM racun WHERE id_racuna = :id")
    suspend fun getRacunById(id: Int): Racun?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRacun(racun: Racun): Long

    @Update
    suspend fun updateRacun(racun: Racun)

    @Delete
    suspend fun deleteRacun(racun: Racun)

    @Query("SELECT datum_racuna FROM racun WHERE id_racuna = :idRacuna")
    suspend fun getDatum(idRacuna: Int): String

}