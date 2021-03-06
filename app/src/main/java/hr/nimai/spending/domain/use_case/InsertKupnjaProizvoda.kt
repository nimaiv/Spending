package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Kupnja
import hr.nimai.spending.domain.model.Proizvod
import hr.nimai.spending.domain.repository.KupnjaRepository
import hr.nimai.spending.domain.repository.ProizvodRepository
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder

class InsertKupnjaProizvoda(
    private val proizvodRepository: ProizvodRepository,
    private val kupnjaRepository: KupnjaRepository
) {
    suspend operator fun invoke(kupnjaProizvoda: KupnjaProizvodaHolder, idRacuna: Int) {
        val proizvod = Proizvod(
            naziv_proizvoda = kupnjaProizvoda.naziv_proizvoda,
            skraceni_naziv_proizvoda = kupnjaProizvoda.skraceni_naziv_proizvoda,
            id_proizvoda = kupnjaProizvoda.id_proizvoda,
            uri_slike = kupnjaProizvoda.uri_slike,
            barkod = kupnjaProizvoda.barkod,
            tip_proizvoda = kupnjaProizvoda.tip_proizvoda
        )
        val idProizvoda  = if (kupnjaProizvoda.id_proizvoda == 0) {
            proizvodRepository.insertProizvod(proizvod)
        } else {
            proizvodRepository.updateProizvod(proizvod)
            proizvod.id_proizvoda
        }

        kupnjaRepository.insertKupnja(Kupnja(
            id_proizvoda = idProizvoda.toInt(),
            id_racuna = idRacuna,
            cijena = kupnjaProizvoda.cijena,
            kolicina = kupnjaProizvoda.kolicina
        ))
    }
}