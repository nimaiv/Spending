package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.repository.KupnjaRepository
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder
import kotlinx.coroutines.flow.Flow

class GetKupnjeProizvodaRacuna(
    private val kupnjaRepository: KupnjaRepository,
) {
    operator fun invoke(idRacuna: Int): Flow<List<KupnjaProizvodaHolder>> {
        return  kupnjaRepository.getKupnjeProizvodaRacuna(idRacuna)
    }
}