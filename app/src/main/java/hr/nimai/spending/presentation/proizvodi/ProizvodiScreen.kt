package hr.nimai.spending.presentation.proizvodi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import hr.nimai.spending.presentation.add_racun.AddRacunEvent
import hr.nimai.spending.presentation.destinations.ProizvodViewScreenDestination
import hr.nimai.spending.presentation.destinations.TipoviProizvodaScreenDestination
import hr.nimai.spending.presentation.proizvodi.components.ProizvodItem

@Composable
@Destination
fun ProizvodiScreen(
    navigator: DestinationsNavigator,
    viewModel: ProizvodiViewModel = hiltViewModel(),
    resultRecipient: ResultRecipient<ProizvodViewScreenDestination, Int>
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                viewModel.onEvent(ProizvodiEvent.RemoveProizvod(result.value))
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Proizvodi",
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { navigator.navigate(TipoviProizvodaScreenDestination) }
                ) {
                    Text(text = "Tipovi proizvoda")
                }
            }
        }
    ) { padding ->

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            item {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = {
                        viewModel.onEvent(ProizvodiEvent.OnSearchQueryChanged(it))
                    },
                    label = { Text("Traži") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.padding(4.dp))
            }
            items(state.proizvodiShown) { proizvod ->
                ProizvodItem(
                    proizvod = proizvod,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigate(ProizvodViewScreenDestination(proizvod.id_proizvoda))
                        },
                )
            }
        }
    }
}