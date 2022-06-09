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
            getRacuniWithTrgovina = GetRacuniWithTrgovina(repository),
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
        kupnjaRepository: KupnjaRepository,
        trgovinaRepository: TrgovinaRepository
    ): AddRacunUseCases {
        return AddRacunUseCases(
            insertRacun = InsertRacun(racunRepository),
            readOCRToRacun = ReadOCRToRacun(),
            extractProductInfoFromOCR = ExtractProductInfoFromOCR(proizvodRepository),
            insertProizvodiKupnja = InsertProizvodiKupnja(proizvodRepository, kupnjaRepository),
            getProizvod = GetProizvod(proizvodRepository),
            getProizvodInfoFromBarcode = GetProizvodInfoFromBarcode(),
            downloadImage = DownloadImage(),
            getTrgovineSuspend = GetTrgovineSuspend(trgovinaRepository)
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
            getTipoviProizvoda = GetTipoviProizvodaSuspend(tipProizvodaRepository),
            insertProizvod = InsertProizvod(proizvodRepository),
            deleteProizvod = DeleteProizvod(proizvodRepository),
            getProizvodInfoFromBarcode = GetProizvodInfoFromBarcode(),
            downloadImage = DownloadImage(),
            deleteProizvodImage = DeleteProizvodImage(proizvodRepository),
            updateSlikaProizvoda = UpdateSlikaProizvoda(proizvodRepository),
            updateProizvod = UpdateProizvod(proizvodRepository)
        )
    }

    @Provides
    @Singleton
    fun provideRacunProizvodiUseCases(
        racunRepository: RacunRepository,
        kupnjaRepository: KupnjaRepository,
        trgovinaRepository: TrgovinaRepository,
        proizvodRepository: ProizvodRepository
    ): RacunProizvodiUseCases {
        return RacunProizvodiUseCases(
            getRacun = GetRacun(racunRepository),
            getKupnjeProizvodaRacuna = GetKupnjeProizvodaRacuna(kupnjaRepository),
            getTrgovina = GetTrgovina(trgovinaRepository),
            getTrgovineSuspend = GetTrgovineSuspend(trgovinaRepository),
            updateRacun = UpdateRacun(racunRepository),
            deleteRacun = DeleteRacun(racunRepository),
            insertKupnjaProizvoda = InsertKupnjaProizvoda(proizvodRepository, kupnjaRepository),
            getProizvodInfoFromBarcode = GetProizvodInfoFromBarcode(),
            downloadImage = DownloadImage(),
            getProizvod = GetProizvod(proizvodRepository),
            deleteKupnja = DeleteKupnja(kupnjaRepository),
            updateKupnjaProizvoda = UpdateKupnjaProizvoda(proizvodRepository, kupnjaRepository)
        )
    }

    @Provides
    @Singleton
    fun provideTrgovineUseCases(
        trgovinaRepository: TrgovinaRepository,
    ): TrgovineUseCases {
        return TrgovineUseCases(
            getTrgovine = GetTrgovine(trgovinaRepository),
            insertTrgovina = InsertTrgovina(trgovinaRepository),
            deleteTrgovina = DeleteTrgovina(trgovinaRepository)
        )
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Provides
    @Singleton
    fun provideBarcodeScanUseCases(): BarcodeScanUseCases {
        return BarcodeScanUseCases(
            parseBarcode = ParseBarcode()
        )
    }

    @Provides
    @Singleton
    fun provideTipoviProizvodaUseCases(
        tipProizvodaRepository: TipProizvodaRepository
    ): TipoviProizvodaUseCases {
        return TipoviProizvodaUseCases(
            getTipoviProizvoda = GetTipoviProizvoda(tipProizvodaRepository),
            insertTipProizvoda = InsertTipProizvoda(tipProizvodaRepository),
            deleteTipProizvoda = DeleteTipProizvoda(tipProizvodaRepository)
        )
    }

    @Provides
    @Singleton
    fun providePotrosnjaUseCases(
        kupnjaRepository: KupnjaRepository,
        proizvodRepository: ProizvodRepository,
        tipProizvodaRepository: TipProizvodaRepository,
        racunRepository: RacunRepository,
        trgovinaRepository: TrgovinaRepository
    ): PotrosnjaUseCases {
        return PotrosnjaUseCases(
            getSpending = GetSpending(
                kupnjaRepository = kupnjaRepository,
                trgovinaRepository = trgovinaRepository,
                racunRepository = racunRepository,
                tipProizvodaRepository = tipProizvodaRepository,
                proizvodRepository = proizvodRepository
            )
        )
    }

    @Provides
    @Singleton
    fun provideSelectSpendingUseCases (
        proizvodRepository: ProizvodRepository,
        tipProizvodaRepository: TipProizvodaRepository,
        trgovinaRepository: TrgovinaRepository
    ): SelectSpendingUseCases {
        return SelectSpendingUseCases(
            getTrgovineSuspend = GetTrgovineSuspend(trgovinaRepository),
            getProizvodiSuspend = GetProizvodiSuspend(proizvodRepository),
            getTipoviProizvodaSuspend = GetTipoviProizvodaSuspend(tipProizvodaRepository)
        )
    }
}