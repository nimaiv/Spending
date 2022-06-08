package hr.nimai.spending.presentation.trgovine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.nimai.spending.presentation.trgovine.composables.TrgovinaDialog
import hr.nimai.spending.presentation.trgovine.composables.TrgovinaItem

@Destination
@Composable
fun TrgovineScreen(
    navigator: DestinationsNavigator,
    viewModel: TrgovineViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(TrgovineEvent.AddTrgovinaDialog)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Dodaj trgovinu")
            }
        },
        topBar = {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Trgovine",
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
                items(state.trgovine) { trgovina ->
                    TrgovinaItem(
                        trgovina = trgovina,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(TrgovineEvent.EditTrgovinaDialog(trgovina))
                            },
                    )
                }
            }
        }
        if (state.isDialogOpen) {
            TrgovinaDialog(viewModel = viewModel)
        }
    }
}