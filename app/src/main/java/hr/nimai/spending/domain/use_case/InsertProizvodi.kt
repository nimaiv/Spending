package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.repository.ProizvodRepository

class InsertProizvodi(
    private val repository: ProizvodRepository
) {

    suspend operator fun invoke() {

    }
}