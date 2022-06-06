package hr.nimai.spending.presentation.proizvod_view

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Destination
fun ProizvodViewScreen(
    idProizvoda: Int,
    navigator: DestinationsNavigator,
    viewModel: ProizvodViewViewModel = hiltViewModel()
) {

    val state = viewModel.state

    var expanded by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProizvodViewViewModel.UiEvent.DeletedProizvod -> {
                    Toast.makeText(context, "Proizvod je uspješno obrisan", Toast.LENGTH_SHORT).show()
                    navigator.navigateUp()
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
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                IconButton(
                    onClick = {
                        navigator.navigateUp()
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Natrag"
                    )
                }

                Button(
                    onClick = { viewModel.onEvent(ProizvodViewEvent.ToggleEdit) },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = state.value.buttonText)
                }
                if (state.value.isEditEnabled) {
                    Button(
                        onClick = { viewModel.onEvent(ProizvodViewEvent.DeleteProizvod) },
                        modifier = Modifier.padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(text = "Obriši")
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
                ) {
                    state.value.tipoviProizvoda.forEach { tipProizvoda ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.onEvent(ProizvodViewEvent.SelectTipProizvoda(tipProizvoda))
                            }
                        ) {

                        }
                    }
                }
            }
            Divider(thickness = 1.dp)
            Text(text = "Kupnje:", style = MaterialTheme.typography.h6)
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