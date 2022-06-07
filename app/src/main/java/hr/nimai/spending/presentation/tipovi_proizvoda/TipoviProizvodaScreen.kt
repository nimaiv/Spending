package hr.nimai.spending.presentation.tipovi_proizvoda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.nimai.spending.presentation.destinations.TipoviProizvodaScreenDestination
import hr.nimai.spending.presentation.tipovi_proizvoda.composables.TipProizvodaDialog
import hr.nimai.spending.presentation.tipovi_proizvoda.composables.TipProizvodaItem

@Composable
@Destination
fun TipoviProizvodaScreen(
    navigator: DestinationsNavigator,
    viewModel: TipoviProizvodaViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(TipoviProizvodaEvent.AddTipProizvodaDialog)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Dodaj tip proizvoda")
            }
        },
        topBar = {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigator.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Natrag")
                }
                Text(
                    text = "Tipovi proizvoda",
                    style = MaterialTheme.typography.h4
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Spacer(modifier = Modifier.height(6.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.tipoviProizvoda) { tipProizvoda ->
                    TipProizvodaItem(
                        tipProizvoda = tipProizvoda,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(
                                    TipoviProizvodaEvent.EditTipProizvodaDialog(
                                        tipProizvoda
                                    )
                                )
                            },
                    )
                }
            }
        }
        if (state.isDialogOpen) {
            TipProizvodaDialog(viewModel = viewModel)
        }
    }
}