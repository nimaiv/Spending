package hr.nimai.spending.presentation.trgovine.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hr.nimai.spending.presentation.add_racun.AddRacunEvent
import hr.nimai.spending.presentation.trgovine.TrgovineEvent
import hr.nimai.spending.presentation.trgovine.TrgovineViewModel

@Composable
fun AddNewTrgovinaDialog(
    viewModel: TrgovineViewModel
){
    val state = viewModel.state.value

    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(TrgovineEvent.DismissDialog)
        },
        title = {
            Text("Nova trgovina")
        },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = state.nazivTrgovine,
                    label = { Text(text = "Naziv trgovine") },
                    onValueChange = {
                        viewModel.onEvent(TrgovineEvent.EnteredNazivTrgovine(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Divider(thickness = 4.dp)
                OutlinedTextField(
                    value = state.adresaTrgovine,
                    label = { Text(text = "Adresa trgovine") },
                    onValueChange = {
                        viewModel.onEvent(TrgovineEvent.EnteredAdresaTrgovine(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.onEvent(TrgovineEvent.DismissDialog)
                    },
                ) {
                    Text("Odustani")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.onEvent(TrgovineEvent.SaveTrgovina)
                    }
                ) {
                    Text("Spremi")
                }
            }
        }
    )
}