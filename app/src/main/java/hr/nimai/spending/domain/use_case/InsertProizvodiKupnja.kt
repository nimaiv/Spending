package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Kupnja
import hr.nimai.spending.domain.model.Proizvod
import hr.nimai.spending.domain.repository.KupnjaRepository
import hr.nimai.spending.domain.repository.ProizvodRepository
import hr.nimai.spending.domain.util.KupnjaProizvodaHolder

class InsertProizvodiKupnja(
    private val proizvodRepository: ProizvodRepository,
    private val kupnjaRepository: KupnjaRepository
) {

    suspend operator fun invoke(proizvodi: List<KupnjaProizvodaHolder>, idRacuna: Int) {

        for (proizvod in proizvodi) {
            val id = proizvodRepository.insertProizvod(Proizvod(
                id_proizvoda = proizvod.id_proizvoda,
                naziv_proizvoda = proizvod.naziv_proizvoda,
                skraceni_naziv_proizvoda = proizvod.skraceni_naziv_proizvoda,
                uri_slike = proizvod.uriSlike,
                barkod = proizvod.barkod
            )).toInt()

            kupnjaRepository.insertKupnja(Kupnja(
                id_proizvoda = id,
                id_racuna = idRacuna,
                kolicina = proizvod.kolicina,
                cijena = proizvod.cijena
            ))
        }
    }
}