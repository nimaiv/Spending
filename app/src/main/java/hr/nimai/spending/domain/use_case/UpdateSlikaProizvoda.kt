package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.repository.ProizvodRepository

class UpdateSlikaProizvoda(
    private val proizvodRepository: ProizvodRepository
) {

    suspend operator fun invoke(idProizvoda: Int, uriSlike: String) {
        proizvodRepository.updateSlikaProizvoda(idProizvoda, uriSlike)
    }
}