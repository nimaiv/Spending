package hr.nimai.spending.presentation.tipovi_proizvoda.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hr.nimai.spending.domain.model.TipProizvoda

@Composable
fun TipProizvodaItem(
    tipProizvoda: TipProizvoda,
    modifier: Modifier = Modifier
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
                    text = tipProizvoda.naziv_tipa_proizvoda,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }

}