package hr.nimai.spending.presentation.racuni.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.nimai.spending.domain.util.OrderType
import hr.nimai.spending.domain.util.RacunOrder

@Composable
fun OrderSection(
    modifier: Modifier,
    racunOrder: RacunOrder = RacunOrder.Datum(OrderType.Descending),
    onOrderChange: (RacunOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Datum",
                selected = racunOrder is RacunOrder.Datum,
                onSelect = { onOrderChange(RacunOrder.Datum(racunOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Iznos",
                selected = racunOrder is RacunOrder.Iznos,
                onSelect = { onOrderChange(RacunOrder.Iznos(racunOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Uzlazno",
                selected = racunOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(racunOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Silazno",
                selected = racunOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(racunOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}