package hr.nimai.spending.presentation.add_racun

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import hr.nimai.spending.presentation.add_racun.components.EditProizvodDialog
import hr.nimai.spending.presentation.add_racun.components.ProizvodItem
import hr.nimai.spending.presentation.destinations.BarcodeScanScreenDestination
import hr.nimai.spending.presentation.destinations.RacuniScreenDestination
import hr.nimai.spending.presentation.destinations.SelectProizvodScreenDestination
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "ShowToast")
@Composable
@Destination
fun AddRacunScreen(
    ocrText: String,
    image: ByteArray,
    navigator: DestinationsNavigator,
    resultRecipientProizvod: ResultRecipient<SelectProizvodScreenDestination, Int>,
    resultRecipientBarcode: ResultRecipient<BarcodeScanScreenDestination, String>,
    viewModel: AddRacunViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val dialogState = viewModel.dialogState.value

    var expanded by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddRacunViewModel.UiEvent.SaveRacun -> {
                    navigator.popBackStack()
                    navigator.popBackStack()
                    navigator.navigate(RacuniScreenDestination)
                }
                is AddRacunViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is AddRacunViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddRacunViewModel.UiEvent.ScanBarcode -> {
                    navigator.navigate(BarcodeScanScreenDestination)
                }
            }
        }
    }

    resultRecipientProizvod.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                viewModel.onEvent(AddRacunEvent.AddExistingProizvod(result.value))
            }
        }
    }

    resultRecipientBarcode.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                viewModel.onEvent(AddRacunEvent.GetDataWithBarcode(result.value))
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddRacunEvent.SaveRacun(context))
                },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Spremi")
            }
        },
        scaffoldState = scaffoldState
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    hr.nimai.spending.presentation.add_racun.components.RacunTextField(
                        text = state.brojRacuna,
                        label = "Broj računa",
                        onValueChange = {
                            viewModel.onEvent(AddRacunEvent.EnteredBrojRacuna(it))
                        },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    hr.nimai.spending.presentation.add_racun.components.RacunTextField(
                        text = state.ukupanIznos,
                        label = "Ukupan iznos",
                        onValueChange = {
                            viewModel.onEvent(AddRacunEvent.EnteredUkupanIznos(it))
                        },
                        textStyle = MaterialTheme.typography.h5,
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    hr.nimai.spending.presentation.add_racun.components.RacunTextField(
                        text = state.datumRacuna,
                        label = "Datum računa",
                        onValueChange = {
                            viewModel.onEvent(AddRacunEvent.EnteredDatumRacuna(it))
                        },
                        textStyle = MaterialTheme.typography.h5,
                        singleLine = true
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
                                .fillMaxWidth()
                        ) {
                            state.trgovine.forEach { trgovina ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(AddRacunEvent.SelectTrgovina(trgovina))
                                        expanded = false
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(text = trgovina.naziv_trgovine)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    hr.nimai.spending.presentation.add_racun.components.RacunTextField(
                        text = state.ocrTekst,
                        label = "Očitan tekst računa",
                        modifier = Modifier.fillMaxHeight(),
                        onValueChange = {

                        },
                        textStyle = MaterialTheme.typography.body1,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Proizvodi:",
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
            itemsIndexed(state.proizvodi) { index, proizvod ->
                ProizvodItem(
                    naziv = proizvod.naziv_proizvoda,
                    cijena = proizvod.cijena,
                    kolicina = proizvod.kolicina,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(
                                AddRacunEvent.OpenDialog(
                                    proizvod = proizvod,
                                    id = index
                                )
                            )
                        },
                    onDeletePress = {
                        viewModel.onEvent(AddRacunEvent.DeleteProizvod(index))
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Button(
                        onClick = {
                            navigator.navigate(SelectProizvodScreenDestination(
                                state.proizvodi.map { it.id_proizvoda }.toHashSet().toIntArray())
                            )
                        },
                        modifier = Modifier.padding(4.dp)
                    )
                    {
                        Text(text = "Dodaj postojeći")
                    }
                    Button(
                        onClick = {
                            viewModel.onEvent(AddRacunEvent.AddNewProizvodDialog)
                        },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = "Dodaj novi")
                    }
                }
            }
        }
        if (dialogState.isDialogOpen) {
            EditProizvodDialog(
                dialogState = dialogState,
                viewModel = viewModel
            )
        }

    }
}