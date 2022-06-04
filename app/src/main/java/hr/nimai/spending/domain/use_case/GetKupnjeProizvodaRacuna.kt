package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.NoProizvodWithIDException
import hr.nimai.spending.domain.repository.KupnjaRepository
import hr.nimai.spending.domain.repository.ProizvodRepository
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder

class GetKupnjeProizvodaRacuna(
    private val proizvodRepository: ProizvodRepository,
    private val kupnjaRepository: KupnjaRepository,
) {
    suspend operator fun invoke(idRacuna: Int): List<KupnjaProizvodaHolder> {
        val kupnje = kupnjaRepository.getKupnjeByIdRacuna(idRacuna)
        val kupnjeProizvoda = mutableListOf<KupnjaProizvodaHolder>()
        kupnje.forEach { kupnja ->
            val proizvod = proizvodRepository.getProizvodById(kupnja.id_proizvoda)
            if (proizvod != null) {
                kupnjeProizvoda.add(KupnjaProizvodaHolder(
                    naziv_proizvoda = proizvod.naziv_proizvoda,
                    skraceni_naziv_proizvoda = proizvod.skraceni_naziv_proizvoda,
                    id_proizvoda = proizvod.id_proizvoda,
                    kolicina = kupnja.kolicina,
                    cijena = kupnja.cijena,
                ))
            } else {
                throw NoProizvodWithIDException("Nema proizvoda s danim ID")
            }
        }

        return kupnjeProizvoda
    }
}