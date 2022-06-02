package hr.nimai.spending.domain.use_case

import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.domain.repository.RacunRepository
import hr.nimai.spending.domain.util.OrderType
import hr.nimai.spending.domain.util.RacunOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class GetRacuni(
    private val repository: RacunRepository
) {

    operator fun invoke(
        racunOrder: RacunOrder = RacunOrder.Datum(OrderType.Descending)
    ): Flow<List<Racun>> {
        val sdtF = SimpleDateFormat("dd.MM.yyyy.", Locale.GERMANY)
        return repository.getAll().map { racuni ->
            when(racunOrder.orderType) {
                is OrderType.Ascending -> {
                    when(racunOrder) {
                        is RacunOrder.Datum -> racuni.sortedBy { sdtF.parse(it.datum_racuna) }
                        is RacunOrder.Iznos -> racuni.sortedBy { it.ukupan_iznos_racuna }
                    }
                }
                is OrderType.Descending -> {
                    when(racunOrder) {
                        is RacunOrder.Datum -> racuni.sortedByDescending { sdtF.parse(it.datum_racuna) }
                        is RacunOrder.Iznos -> racuni.sortedByDescending { it.ukupan_iznos_racuna }
                    }
                }
            }
        }
    }


}