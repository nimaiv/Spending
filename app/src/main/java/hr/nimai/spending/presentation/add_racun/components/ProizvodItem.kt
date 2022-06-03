package hr.nimai.spending.presentation.add_racun.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProizvodItem(
    naziv: String,
    cijena: Double,
    kolicina: Int,
    modifier: Modifier = Modifier,
    onDeletePress: () -> Unit
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
                    text = naziv,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Količina: $kolicina",
                    style = MaterialTheme.typography.body1
                )
            }

            Text(
                text = cijena.toString() + "kn",
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            IconButton(
                onClick = {
                    onDeletePress()
                },
                modifier = Modifier.align(Alignment.Bottom)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = "Obriši"
                )
            }


        }
    }
}