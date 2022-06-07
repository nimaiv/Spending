package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.TipProizvoda
import hr.nimai.spending.domain.repository.TipProizvodaRepository

class DeleteTipProizvoda(
    private val tipProizvodaRepository: TipProizvodaRepository
) {

    suspend operator fun invoke(idTipaProizvoda: Int) {
        tipProizvodaRepository.deleteTipProizvoda(idTipaProizvoda)
    }
}