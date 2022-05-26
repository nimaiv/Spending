package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.InvalidRacunException
import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.repository.RacunRepository

class InsertRacun(
    private val repository: RacunRepository
) {

    @Throws(InvalidRacunException::class)
    suspend operator fun invoke(racun: Racun) {
        if(racun.broj_racuna.isBlank()) {
            throw InvalidRacunException("Broj računa ne smije biti prazan.")
        }
        if(racun.ukupan_iznos_racuna.isNaN()) {
            throw InvalidRacunException("Ukupan iznos računa nije valjan")
        }
        if(!racun.datum_racuna.matches(Regex("""\d\d\.\d\d\.\d\d\d\d\."""))) {
            throw InvalidRacunException("Format datuma nije valjdan (dd.MM.yyyy.)")
        }
        repository.insertRacun(racun)
    }
}