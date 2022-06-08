package hr.nimai.spending.presentation.select_spending

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
import com.ramcosta.composedestinations.result.ResultBackNavigator
import hr.nimai.spending.domain.util.TypedItemSerializable

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun SelectSpendingScreen(
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<TypedItemSerializable>,
    viewModel: SelectSpendingViewModel = hiltViewModel()
) {
    
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value
    
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigator.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Natrag")
                }
                Text(
                    text = "Odaberi stavku",
                    style = MaterialTheme.typography.h4
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.onEvent(SelectSpendingEvent.SelectProizvodi) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Proizvodi", style = MaterialTheme.typography.caption)
                }
                Button(
                    onClick = { viewModel.onEvent(SelectSpendingEvent.SelectTrgovine) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Trgovine", style = MaterialTheme.typography.caption)
                }
                Button(
                    onClick = { viewModel.onEvent(SelectSpendingEvent.SelectTipoviProizvoda) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Tipovi proizvoda", style = MaterialTheme.typography.caption)
                }
            }
            Divider(thickness = 2.dp, modifier = Modifier.padding(8.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                val items = when (state.content) {
                    Screens.PROIZVODI -> {
                        state.proizvodi
                    }
                    Screens.TRGOVINE -> {
                        state.trgovine
                    }
                    Screens.TIPOVIPROIZVODA -> {
                        state.tipoviProizvoda
                    }
                }
                items(items) { item ->
                    Divider(thickness = 2.dp, modifier = Modifier.padding(vertical = 4.dp))
                    Surface(
                        onClick = {
                            resultBackNavigator.navigateBack(TypedItemSerializable(item.id, state.content))
                        },
                        color = MaterialTheme.colors.background
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = item.naziv,
                                modifier = Modifier.weight(2f)
                            )
                        }
                    }
                }
            }
        }
    }
}