package hr.nimai.spending.presentation.proizvodi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.nimai.spending.presentation.destinations.ProizvodViewScreenDestination
import hr.nimai.spending.presentation.proizvodi.components.ProizvodItem

@Composable
@Destination
fun ProizvodiScreen(
    navigator: DestinationsNavigator,
    viewModel: ProizvodiViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    
    Scaffold(
        scaffoldState = scaffoldState,
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
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
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