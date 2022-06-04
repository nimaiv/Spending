package hr.nimai.spending.di

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.nimai.spending.data.data_source.SpendingDatabase
import hr.nimai.spending.data.repository.*
import hr.nimai.spending.domain.repository.*
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
    fun provideProizvodRepository(db: SpendingDatabase): ProizvodRepository {
        return ProizvodRepositoryImpl(db.proizvodDao())
    }

    @Provides
    @Singleton
    fun provideKupnjaRepository(db: SpendingDatabase): KupnjaRepository {
        return KupnjaRepositoryImpl(db.kupnjaDao())
    }

    @Provides
    @Singleton
    fun provideTipProizvodaRepository(db: SpendingDatabase): TipProizvodaRepository {
        return TipProizvodaRepositoryImpl(db.tipProizvodaDao())
    }

    @Provides
    @Singleton
    fun provideTrgovinaRepository(db: SpendingDatabase): TrgovinaRepository {
        return TrgovinaRepositoryImpl(db.trgovinaDao())
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
    fun provideAddRacunUseCases(
        racunRepository: RacunRepository,
        proizvodRepository: ProizvodRepository,
        kupnjaRepository: KupnjaRepository
    ): AddRacunUseCases {
        return AddRacunUseCases(
            insertRacun = InsertRacun(racunRepository),
            readOCRToRacun = ReadOCRToRacun(),
            extractProductInfoFromOCR = ExtractProductInfoFromOCR(proizvodRepository),
            insertProizvodiKupnja = InsertProizvodiKupnja(proizvodRepository, kupnjaRepository),
            getProizvod = GetProizvod(proizvodRepository)
        )
    }

    @Provides
    @Singleton
    fun provideProizvodiUseCases(
        proizvodRepository: ProizvodRepository
    ): ProizvodiUseCases {
        return ProizvodiUseCases(
            getProizvodi = GetProizvodi(proizvodRepository)
        )
    }

    @Provides
    @Singleton
    fun provideProizvodViewUseCases(
        proizvodRepository: ProizvodRepository,
        kupnjaRepository: KupnjaRepository,
        tipProizvodaRepository: TipProizvodaRepository,
        racunRepository: RacunRepository
    ): ProizvodViewUseCases {
        return ProizvodViewUseCases(
            getProizvod = GetProizvod(proizvodRepository),
            getKupnjeProizvoda = GetKupnjeProizvoda(kupnjaRepository, racunRepository),
            getTipoviProizvoda = GetTipoviProizvoda(tipProizvodaRepository),
            insertProizvod = InsertProizvod(proizvodRepository)
        )
    }

    @Provides
    @Singleton
    fun provideRacunProizvodiUseCases(
        racunRepository: RacunRepository,
        proizvodRepository: ProizvodRepository,
        kupnjaRepository: KupnjaRepository
    ): RacunProizvodiUseCases {
        return RacunProizvodiUseCases(
            getRacun = GetRacun(racunRepository),
            getKupnjeProizvodaRacuna = GetKupnjeProizvodaRacuna(proizvodRepository, kupnjaRepository)
        )
    }

    @Provides
    @Singleton
    fun provideTrgovineUseCases(
        trgovinaRepository: TrgovinaRepository,
    ): TrgovineUseCases {
        return TrgovineUseCases(
            getTrgovine = GetTrgovine(trgovinaRepository),
            insertTrgovina = InsertTrgovina(trgovinaRepository)
        )
    }

}