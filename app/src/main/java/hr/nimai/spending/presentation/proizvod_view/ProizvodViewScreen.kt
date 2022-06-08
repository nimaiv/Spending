package hr.nimai.spending.presentation.proizvod_view

import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import hr.nimai.spending.R
import hr.nimai.spending.presentation.destinations.BarcodeScanScreenDestination
import hr.nimai.spending.presentation.destinations.ImageScreenDestination
import hr.nimai.spending.presentation.destinations.TakePhotoScreenDestination
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Destination
fun ProizvodViewScreen(
    idProizvoda: Int,
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<Int>,
    barcodeResultRecipient: ResultRecipient<BarcodeScanScreenDestination, String>,
    imageResultRecipient: ResultRecipient<TakePhotoScreenDestination, ByteArray>,
    viewModel: ProizvodViewViewModel = hiltViewModel()
) {

    val state = viewModel.state

    var expanded by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    barcodeResultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> { }
            is NavResult.Value -> {
                viewModel.onEvent(ProizvodViewEvent.GetDataWithBarcode(result.value, context))
            }
        }
    }

    imageResultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> { }
            is NavResult.Value -> {
                viewModel.onEvent(ProizvodViewEvent.NewSlika(result.value, context))
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProizvodViewViewModel.UiEvent.DeletedProizvod -> {
                    Toast.makeText(context, "Proizvod je uspješno obrisan", Toast.LENGTH_SHORT).show()
                    resultBackNavigator.navigateBack(event.idProizvoda)
                }
                is ProizvodViewViewModel.UiEvent.LoadSlika -> {
                    viewModel.onEvent(ProizvodViewEvent.LoadSlika(context))
                }
                is ProizvodViewViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        resultBackNavigator.navigateBack()
                    },
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Natrag"
                    )
                }

                Button(
                    onClick = { viewModel.onEvent(ProizvodViewEvent.ToggleEdit(context)) },
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = state.value.buttonColor)
                ) {
                    Text(
                        text = state.value.buttonText,
                    )
                }
                if (state.value.isEditEnabled) {
                    Button(
                        onClick = { viewModel.onEvent(ProizvodViewEvent.DeleteProizvod(context)) },
                        modifier = Modifier.padding(horizontal = 2.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White)
                    ) {
                        Text(text = "Obriši")
                    }
                    Button(
                        onClick = { navigator.navigate(BarcodeScanScreenDestination) },
                        modifier = Modifier.padding(horizontal = 2.dp),
                    ) {
                        Text(text = "Skeniraj")
                    }
                }
            }
            OutlinedTextField(
                value = state.value.nazivProizvoda,
                label = { Text(text = "Naziv proizvoda") },
                onValueChange = {
                    viewModel.onEvent(ProizvodViewEvent.EnteredNazivProizvoda(it))
                },
                enabled = state.value.isEditEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            OutlinedTextField(
                value = state.value.skraceniNazivProizvoda,
                label = { Text(text = "Skraćeni naziv proizvoda") },
                onValueChange = {
                    viewModel.onEvent(ProizvodViewEvent.EnteredSkraceniNazivProizvoda(it))
                },
                enabled = state.value.isEditEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            OutlinedTextField(
                value = state.value.barkod?: "",
                label = { Text(text = "Barkod proizvoda") },
                onValueChange = {
                    viewModel.onEvent(ProizvodViewEvent.EnteredBarkod(it))
                },
                enabled = state.value.isEditEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    if (state.value.isEditEnabled) {
                        expanded = !expanded
                    }
                }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = state.value.nazivTipaProizvoda,
                    onValueChange = {},
                    label = { Text("Tip proizvoda") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    enabled = state.value.isEditEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    state.value.tipoviProizvoda.forEach { tipProizvoda ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onEvent(ProizvodViewEvent.SelectTipProizvoda(tipProizvoda))
                                expanded = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(text = tipProizvoda.naziv_tipa_proizvoda)
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Slika:",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Card(
                    modifier = Modifier
                        .size(108.dp)
                        .padding(8.dp),
                    elevation = 2.dp,
                    onClick = {
                        if (state.value.slika != null) {
                            navigator.navigate(ImageScreenDestination(state.value.slika!!))
                        }
                    }
                ) {
                    if (state.value.slika != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(context).data(state.value.slika).build(),
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
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Button(
                        onClick = {
                                  navigator.navigate(TakePhotoScreenDestination)
                        },
                        modifier = Modifier.padding(4.dp),
                    ) {
                        Text(text = "Nova slika")
                    }
                    if (state.value.slika != null) {
                        Button(
                            onClick = { viewModel.onEvent(ProizvodViewEvent.DeleteImage(context)) },
                            modifier = Modifier.padding(horizontal = 4.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White)
                        ) {
                            Text(text = "Obriši sliku")
                        }
                    }
                }
            }
            Divider(thickness = 1.dp)
            Text(
                text = "Kupnje:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.value.kupnje) { kupnja ->
                    Row {
                        Text(
                            text = "Cijena: ${kupnja.kupnja.cijena}kn",
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Datum: ${kupnja.datum}",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Divider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}