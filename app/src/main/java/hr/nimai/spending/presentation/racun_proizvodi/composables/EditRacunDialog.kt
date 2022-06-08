package hr.nimai.spending.presentation.racun_proizvodi.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import hr.nimai.spending.presentation.racun_proizvodi.RacunProizvodiEvent
import hr.nimai.spending.presentation.racun_proizvodi.RacunProizvodiViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditRacunDialog(
    viewModel: RacunProizvodiViewModel
) {
    val state = viewModel.editRacunDialogState.value

    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { viewModel.onEvent(RacunProizvodiEvent.DismissDialog) },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = state.brojRacuna,
                    label = {
                            Text(text = "Broj računa")
                    },
                    onValueChange = {
                        viewModel.onEvent(RacunProizvodiEvent.EnteredBrojRacuna(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = state.isBrojRacunaError,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = state.datumRacuna,
                    label = {
                        Text(text = "Datum računa")
                    },
                    onValueChange = {
                        viewModel.onEvent(RacunProizvodiEvent.EnteredDatumRacuna(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = state.isDatumRacunaError,
                )
                if (state.isDatumRacunaError) {
                    Text(
                        text = "Datum treba biti formata 'dd.MM.yyyy.'",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = MaterialTheme.typography.body2.fontSize
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = state.ukupanIznosRacuna,
                    label = {
                        Text(text = "Ukupan iznos računa")
                    },
                    onValueChange = {
                        viewModel.onEvent(RacunProizvodiEvent.EnteredUkupanIznosRacuna(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isError = state.isUkupanIznosError,
                )
                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = state.nazivTrgovine,
                        onValueChange = {},
                        label = { Text("Trgovina") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        modifier = Modifier
                    ) {
                        state.trgovine.forEach { trgovina ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.onEvent(RacunProizvodiEvent.SelectTrgovina(trgovina))
                                    expanded = false
                                },
                                modifier = Modifier
                            ) {
                                Text(text = trgovina.naziv_trgovine)
                            }
                        }
                    }
                }
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
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.onEvent(RacunProizvodiEvent.DeleteRacun(context)) },
                    modifier = Modifier.padding(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White)
                ) {
                    Text(text = "Obriši")
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
                        viewModel.onEvent(RacunProizvodiEvent.SaveRacun)
                    }
                ) {
                    Text("Spremi")
                }
            }
        }
    )
}