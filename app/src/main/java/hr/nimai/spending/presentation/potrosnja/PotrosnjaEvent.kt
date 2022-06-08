package hr.nimai.spending.presentation.potrosnja

import androidx.core.util.Pair
import hr.nimai.spending.domain.util.TypedItemSerializable

sealed class PotrosnjaEvent {
    data class OnDatePick(val dates: Pair<Long,Long>): PotrosnjaEvent()
    data class DisplaySpending(val item: TypedItemSerializable): PotrosnjaEvent()
}
