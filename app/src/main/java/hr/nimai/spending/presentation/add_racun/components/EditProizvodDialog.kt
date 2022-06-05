package hr.nimai.spending.presentation.add_racun.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import hr.nimai.spending.presentation.add_racun.AddRacunEvent
import hr.nimai.spending.presentation.add_racun.AddRacunViewModel
import hr.nimai.spending.presentation.add_racun.DialogState

@Composable
fun EditProizvodDialog(
    dialogState: DialogState,
    viewModel: AddRacunViewModel,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(AddRacunEvent.DismissDialog)
        },
        title = {
            Text(text = "Uredi podatke o proizvodu")
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { viewModel.onEvent(AddRacunEvent.ScanBarcode) }
                ) {
                    Text(text = "Skeniraj")
                }
                RacunTextField(
                    text = dialogState.nazivProizvoda,
                    label = "Naziv proizvoda",
                    onValueChange = {
                        viewModel.onEvent(AddRacunEvent.EnteredNazivProizvoda(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = dialogState.isNazivEmptyError,
                )
                Spacer(modifier = Modifier.height(16.dp))
                RacunTextField(
                    text = dialogState.skraceniNazivProizvoda,
                    label = "Skraćeni naziv proizvoda",
                    onValueChange = {
                        viewModel.onEvent(AddRacunEvent.EnteredSkraceniNazivProizvoda(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = dialogState.isSkraceniNazivEmptyError
                )
                Spacer(modifier = Modifier.height(16.dp))
                RacunTextField(
                    text = dialogState.barkod,
                    label = "Barkod",
                    onValueChange = {

                    },
                    textStyle = MaterialTheme.typography.body1,
                    isEnabled = false,
                )
                Spacer(modifier = Modifier.height(16.dp))
                RacunTextField(
                    text = dialogState.cijenaProizvoda,
                    label = "Cijena proizvoda",
                    onValueChange = {
                        viewModel.onEvent(AddRacunEvent.EnteredCijenaProizvoda(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = dialogState.isCijenaError
                )
                Spacer(modifier = Modifier.height(16.dp))
                RacunTextField(
                    text = dialogState.kolicinaProizvoda,
                    label = "Količina",
                    onValueChange = {
                        viewModel.onEvent(AddRacunEvent.EnteredKolicinaProizvoda(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = dialogState.isKolicinaError
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (dialogState.showErrorMessage) {
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
                        viewModel.onEvent(AddRacunEvent.DismissDialog)
                    }
                ) {
                    Text("Odustani")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.onEvent(AddRacunEvent.EditProizvodValues)
                    }
                ) {
                    Text("Spremi")
                }
            }
        }
    )



}