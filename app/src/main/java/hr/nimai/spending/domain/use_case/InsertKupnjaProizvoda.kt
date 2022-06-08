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
        val idProizvoda = proizvodRepository.insertProizvod(Proizvod(
            naziv_proizvoda = kupnjaProizvoda.naziv_proizvoda,
            skraceni_naziv_proizvoda = kupnjaProizvoda.skraceni_naziv_proizvoda,
            id_proizvoda = kupnjaProizvoda.id_proizvoda,
            uri_slike = kupnjaProizvoda.uriSlike,
            barkod = kupnjaProizvoda.barkod
        )).toInt()
        kupnjaRepository.insertKupnja(Kupnja(
            id_proizvoda = idProizvoda,
            id_racuna = idRacuna,
            cijena = kupnjaProizvoda.cijena,
            kolicina = kupnjaProizvoda.kolicina
        ))
    }
}