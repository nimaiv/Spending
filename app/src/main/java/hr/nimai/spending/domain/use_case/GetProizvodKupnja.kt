package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.repository.ProizvodRepository
import hr.nimai.spending.domain.util.ProizvodKupnjaHolder

class GetProizvodKupnja(
    private val repository: ProizvodRepository
) {

    suspend operator fun invoke(skraceniNazivProizvoda: String) {

    }
}