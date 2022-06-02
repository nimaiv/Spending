package hr.nimai.spending.presentation.proizvodi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            items(state.proizvodi) { proizvod ->
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