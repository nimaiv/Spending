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

        for (proizvodKupnja in proizvodi) {
            val proizvod = Proizvod(
                id_proizvoda = proizvodKupnja.id_proizvoda,
                naziv_proizvoda = proizvodKupnja.naziv_proizvoda,
                skraceni_naziv_proizvoda = proizvodKupnja.skraceni_naziv_proizvoda,
                uri_slike = proizvodKupnja.uri_slike,
                barkod = proizvodKupnja.barkod,
                tip_proizvoda = proizvodKupnja.tip_proizvoda
            )
            val id = if (proizvod.id_proizvoda == 0) {
                proizvodRepository.insertProizvod(proizvod).toInt()
            } else {
                proizvodRepository.updateProizvod(proizvod)
                proizvod.id_proizvoda
            }

            kupnjaRepository.insertKupnja(Kupnja(
                id_proizvoda = id,
                id_racuna = idRacuna,
                kolicina = proizvodKupnja.kolicina,
                cijena = proizvodKupnja.cijena
            ))
        }
    }
}