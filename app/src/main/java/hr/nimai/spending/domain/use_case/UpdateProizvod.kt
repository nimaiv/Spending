package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Proizvod
import hr.nimai.spending.domain.repository.ProizvodRepository

class UpdateProizvod(
    private val proizvodRepository: ProizvodRepository
) {
    suspend operator fun invoke(proizvod: Proizvod) {
        proizvodRepository.updateProizvod(proizvod)
    }
}