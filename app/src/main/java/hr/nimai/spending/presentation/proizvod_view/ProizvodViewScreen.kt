package hr.nimai.spending.presentation.proizvod_view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.nimai.spending.presentation.racuni.components.RacunItem

@Composable
@Destination
fun ProizvodViewScreen(
    idProizvoda: Int,
    navigator: DestinationsNavigator,
    viewModel: ProizvodViewViewModel = hiltViewModel()
) {

    val state = viewModel.state

    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    var tipoviTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Button(
                onClick = { viewModel.onEvent(ProizvodViewEvent.ToggleEdit) },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                Text(text = state.value.buttonText)
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
            OutlinedTextField(
                value = state.value.nazivTipaProizvoda,
                label = { Text(text = "Tip proizvoda") },
                onValueChange = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .onGloballyPositioned { coordinates ->
                        tipoviTextFieldSize = coordinates.size.toSize()
                    },
                enabled = state.value.isEditEnabled,
                trailingIcon = {
                    Icon(icon,"",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(with(LocalDensity.current){tipoviTextFieldSize.width.toDp()}).padding(16.dp)
            ) {
                state.value.tipoviProizvoda.forEach {  tipProizvoda ->
                    DropdownMenuItem(
                        onClick = {
                            viewModel.onEvent(ProizvodViewEvent.OnDropdownItemSelect(tipProizvoda))
                            expanded = false
                        }
                    ) {
                        Text(text = tipProizvoda.naziv_tipa_proizvoda)
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