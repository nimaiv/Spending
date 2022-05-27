package hr.nimai.spending.di

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.nimai.spending.data.data_source.SpendingDatabase
import hr.nimai.spending.data.repository.RacunRepositoryImpl
import hr.nimai.spending.domain.repository.RacunRepository
import hr.nimai.spending.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSpendingDatabase(app: Application): SpendingDatabase {
        return Room.databaseBuilder(
            app,
            SpendingDatabase::class.java,
            SpendingDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRacunRepository(db: SpendingDatabase): RacunRepository {
        return RacunRepositoryImpl(db.racunDao())
    }

    @Provides
    @Singleton
    fun provideRacunUseCases(repository: RacunRepository): RacunUseCases {
        return RacunUseCases(
            getRacuni = GetRacuni(repository),
            deleteRacun = DeleteRacun(repository)
        )
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Provides
    @Singleton
    fun provideRacunScanUseCases(): RacunScanUseCases {
        return RacunScanUseCases(
            parseScanRacuna = ParseScanRacuna()
        )
    }

    @Provides
    @Singleton
    fun provideAddRacunUseCases(repository: RacunRepository): AddRacunUseCases {
        return AddRacunUseCases(
            insertRacun = InsertRacun(repository),
            readOCRToRacun = ReadOCRToRacun(),
            extractProductInfoFromOCR = ExtractProductInfoFromOCR()
        )
    }

}