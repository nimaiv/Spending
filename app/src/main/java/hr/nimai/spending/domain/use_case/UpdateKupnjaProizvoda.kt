package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Kupnja
import hr.nimai.spending.domain.model.Proizvod
import hr.nimai.spending.domain.repository.KupnjaRepository
import hr.nimai.spending.domain.repository.ProizvodRepository
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder

class UpdateKupnjaProizvoda(
    private val proizvodRepository: ProizvodRepository,
    private val kupnjaRepository: KupnjaRepository,
) {

    suspend operator fun invoke(kupnjaProizvoda: KupnjaProizvodaHolder, idRacuna: Int) {

        proizvodRepository.updateProizvod(Proizvod(
            naziv_proizvoda = kupnjaProizvoda.naziv_proizvoda,
            skraceni_naziv_proizvoda = kupnjaProizvoda.skraceni_naziv_proizvoda,
            id_proizvoda = kupnjaProizvoda.id_proizvoda,
            uri_slike = kupnjaProizvoda.uri_slike,
            barkod = kupnjaProizvoda.barkod,
            tip_proizvoda = kupnjaProizvoda.tip_proizvoda,
        ))

        kupnjaRepository.updateKupnja(Kupnja(
            id_proizvoda = kupnjaProizvoda.id_proizvoda,
            id_racuna = idRacuna,
            cijena = kupnjaProizvoda.cijena,
            kolicina = kupnjaProizvoda.kolicina
        ))
    }
}