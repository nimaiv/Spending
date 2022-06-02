package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.NoProizvodWithIDException
import hr.nimai.spending.domain.model.Proizvod
import hr.nimai.spending.domain.repository.ProizvodRepository

class GetProizvod(
    private val repository: ProizvodRepository
) {
    suspend operator fun invoke(idProizvoda: Int): Proizvod {
        val proizvod = repository.getProizvodById(id = idProizvoda)

        if (proizvod == null) {
            throw NoProizvodWithIDException("Ne postoji proizvod s danim ID")
        } else {
            return proizvod
        }
    }
}