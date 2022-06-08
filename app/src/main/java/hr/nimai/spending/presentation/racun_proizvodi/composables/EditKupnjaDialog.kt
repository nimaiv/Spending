package hr.nimai.spending.presentation.racun_proizvodi.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import hr.nimai.spending.presentation.add_racun.AddRacunEvent
import hr.nimai.spending.presentation.add_racun.components.RacunTextField
import hr.nimai.spending.presentation.racun_proizvodi.RacunProizvodiEvent
import hr.nimai.spending.presentation.racun_proizvodi.RacunProizvodiViewModel

@Composable
fun EditKupnjaDialog(
    viewModel: RacunProizvodiViewModel,
    modifier: Modifier = Modifier,
    onSelectExistingProizvod: () -> Unit
) {
    val state = viewModel.editKupnjaDialogState.value
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(RacunProizvodiEvent.DismissDialog)
        },
        title = {
            Text(text = "Uredi podatke o proizvodu")
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                if (state.isNew) {
                    Button(onClick = onSelectExistingProizvod) {
                        Text(text = "Učitaj postojeći proizvod")
                    }
                } else {
                    Button(
                        onClick = { viewModel.onEvent(RacunProizvodiEvent.DeleteKupnja) },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White)
                    ) {
                        Text(text = "Obriši")
                    }
                }
                Button(
                    onClick = { viewModel.onEvent(RacunProizvodiEvent.ScanBarcode) }
                ) {
                    Text(text = "Skeniraj")
                }


                RacunTextField(
                    text = state.nazivProizvoda,
                    label = "Naziv proizvoda",
                    onValueChange = {
                        viewModel.onEvent(RacunProizvodiEvent.EnteredNazivProizvoda(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = state.isNazivEmptyError,
                )
                Spacer(modifier = Modifier.height(16.dp))
                RacunTextField(
                    text = state.skraceniNazivProizvoda,
                    label = "Skraćeni naziv proizvoda",
                    onValueChange = {
                        viewModel.onEvent(RacunProizvodiEvent.EnteredSkraceniNazivProizvoda(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = state.isSkraceniNazivEmptyError
                )
                Spacer(modifier = Modifier.height(16.dp))
                RacunTextField(
                    text = state.barkod,
                    label = "Barkod",
                    onValueChange = {

                    },
                    textStyle = MaterialTheme.typography.body1,
                    isEnabled = false,
                )
                Spacer(modifier = Modifier.height(16.dp))
                RacunTextField(
                    text = state.cijenaProizvoda,
                    label = "Cijena proizvoda",
                    onValueChange = {
                        viewModel.onEvent(RacunProizvodiEvent.EnteredCijenaProizvoda(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = state.isCijenaError
                )
                Spacer(modifier = Modifier.height(16.dp))
                RacunTextField(
                    text = state.kolicinaProizvoda,
                    label = "Količina",
                    onValueChange = {
                        viewModel.onEvent(RacunProizvodiEvent.EnteredKolicinaProizvoda(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = state.isKolicinaError
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (state.showErrorMessage) {
                    Text(
                        text = "Unesene vrijednosti nisu ispravne!",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = MaterialTheme.typography.body2.fontSize
                        )
                    )
                }
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.onEvent(RacunProizvodiEvent.DismissDialog)
                    }
                ) {
                    Text("Odustani")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.onEvent(RacunProizvodiEvent.SaveKupnja(context))
                    }
                ) {
                    Text("Spremi")
                }
            }
        }
    )
}