package hr.nimai.spending.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hr.nimai.spending.domain.model.*

@Database(
    entities = [Racun::class,
                Proizvod::class,
                Kupnja::class,
                Trgovina::class,
                TipProizvoda::class,
                Fransiza::class],
    version = 1
)
abstract class SpendingDatabase : RoomDatabase() {
    abstract fun racunDao(): RacunDao
    abstract fun kupnjaDao(): KupnjaDao
    abstract fun proizvodDao(): ProizvodDao
    abstract fun trgovinaDao(): TrgovinaDao
    abstract fun tipProizvodaDao(): TipProizvodaDao
    abstract fun fransizaDao(): FransizaDao

    companion object {
        const val DATABASE_NAME = "spending_db"
    }
}