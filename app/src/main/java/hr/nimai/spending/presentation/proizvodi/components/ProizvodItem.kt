package hr.nimai.spending.presentation.proizvodi.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import hr.nimai.spending.R
import hr.nimai.spending.domain.model.Proizvod
import java.io.File

@Composable
fun ProizvodItem(
    proizvod: Proizvod,
    modifier: Modifier = Modifier,
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
                    text = proizvod.naziv_proizvoda,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = proizvod.skraceni_naziv_proizvoda,
                    style = MaterialTheme.typography.body2
                )
            }
            Card(
                modifier = Modifier.size(64.dp),
                elevation = 2.dp
            ) {
                if (proizvod.uri_slike != null) {
                    Image(
                        rememberAsyncImagePainter(model = File(proizvod.uri_slike)),
                        contentDescription = "Slika",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_outline_image_not_supported_24),
                        contentDescription = "Slika",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

        }
    }
}