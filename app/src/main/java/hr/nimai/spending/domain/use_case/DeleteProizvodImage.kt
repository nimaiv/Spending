package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.repository.ProizvodRepository

class DeleteProizvodImage(
    private val proizvodRepository: ProizvodRepository
) {

    suspend operator fun invoke(idProizvoda: Int) {
        proizvodRepository.deleteSlikuProizvoda(idProizvoda)
    }
}