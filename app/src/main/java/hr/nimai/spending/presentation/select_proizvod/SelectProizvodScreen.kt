package hr.nimai.spending.presentation.select_proizvod

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import hr.nimai.spending.presentation.proizvodi.ProizvodiEvent
import hr.nimai.spending.presentation.proizvodi.components.ProizvodItem

@Destination
@Composable
fun SelectProizvodScreen(
    filterProizovdi: IntArray?,
    resultNavigator: ResultBackNavigator<Int>,
    viewModel: SelectProizvodViewModel = hiltViewModel()
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
                Text(
                    text = "Odaberi proizvod",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(16.dp)
                )
                OutlinedTextField(
                    value = state.query,
                    onValueChange = {
                        viewModel.onEvent(SelectProizvodiEvent.OnSearchQueryChanged(it))
                    },
                    label = { Text("Traži") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            items(state.proizvodiShown) { proizvod ->
                ProizvodItem(
                    proizvod = proizvod,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            resultNavigator.navigateBack(proizvod.id_proizvoda)
                        },
                )
            }
        }
    }
}