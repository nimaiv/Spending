package hr.nimai.spending.presentation.racuni.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RacunItem(
    datum: String,
    iznos: Double,
    modifier: Modifier = Modifier,
    naziv_trgovine: String = "",
) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = datum,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = naziv_trgovine,
                    style = MaterialTheme.typography.h6
                )
            }
            Text(
                text = iznos.toString() + "kn",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }
}