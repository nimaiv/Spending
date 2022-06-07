package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.TipProizvoda
import hr.nimai.spending.domain.repository.TipProizvodaRepository

class InsertTipProizvoda(
    private val tipProizvodaRepository: TipProizvodaRepository
) {

    suspend operator fun invoke(tipProizvoda: TipProizvoda) {
        tipProizvodaRepository.insertTipProizvoda(tipProizvoda)
    }
}