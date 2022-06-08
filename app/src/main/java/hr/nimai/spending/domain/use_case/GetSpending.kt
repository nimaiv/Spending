package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.repository.*
import hr.nimai.spending.presentation.select_spending.Screens
import java.text.SimpleDateFormat
import java.util.*

class GetSpending(
    private val kupnjaRepository: KupnjaRepository,
    private val proizvodRepository: ProizvodRepository,
    private val tipProizvodaRepository: TipProizvodaRepository,
    private val trgovinaRepository: TrgovinaRepository,
    private val racunRepository: RacunRepository,
) {

    suspend operator fun invoke(item: Pair<Screens, Int>, startDate: String, endDate: String): Pair<String, List<Pair<String, Double>>> {
        val sdtF = SimpleDateFormat("dd.MM.yyyy.", Locale.GERMANY)
        try {
            return when (item.first) {
                Screens.PROIZVODI -> {
                    val naziv = proizvodRepository.getProizvodById(item.second)?.naziv_proizvoda!!
                    val kupnje = GetKupnjeProizvoda(
                        racunRepository = racunRepository, kupnjaRepository = kupnjaRepository
                    ).invoke(item.second)
                    Pair(naziv, kupnje.map { Pair(it.datum, it.kupnja.cijena*it.kupnja.kolicina) }
                        .filter { sdtF.parse(it.first)!! >= sdtF.parse(startDate) &&
                                    sdtF.parse(it.first)!! <= sdtF.parse(endDate) }
                        .sortedByDescending { sdtF.parse(it.first) })
                }
                Screens.TRGOVINE -> {
                    val naziv = trgovinaRepository.getTrgovinaById(item.second)?.naziv_trgovine!!
                    val kupnje = racunRepository.getRacuniByIdTrgovine(item.second)
                    Pair(naziv, kupnje.map { Pair(it.datum, it.iznos) }
                        .filter { sdtF.parse(it.first)!! >= sdtF.parse(startDate) &&
                            sdtF.parse(it.first)!! <= sdtF.parse(endDate) }
                        .sortedByDescending { sdtF.parse(it.first) })
                }
                Screens.TIPOVIPROIZVODA -> {
                    val naziv = tipProizvodaRepository.getTipProizvodaById(item.second)?.naziv_tipa_proizvoda!!
                    val kupnje = kupnjaRepository.getKupnjeTipaProizvoda(item.second)
                    Pair(naziv, kupnje.map { Pair(it.datum, it.iznos) }
                        .filter { sdtF.parse(it.first)!! >= sdtF.parse(startDate) &&
                                sdtF.parse(it.first)!! <= sdtF.parse(endDate) }
                        .sortedByDescending { sdtF.parse(it.first) })
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}