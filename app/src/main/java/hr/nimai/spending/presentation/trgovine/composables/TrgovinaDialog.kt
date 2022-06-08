package hr.nimai.spending.presentation.trgovine.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import hr.nimai.spending.presentation.trgovine.TrgovineEvent
import hr.nimai.spending.presentation.trgovine.TrgovineViewModel

@Composable
fun TrgovinaDialog(
    viewModel: TrgovineViewModel
){
    val state = viewModel.state.value

    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(TrgovineEvent.DismissDialog)
        },
        title = {
            Text(
                text = "Nova trgovina",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h6
            )
        },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = state.nazivTrgovine,
                    label = { Text(text = "Naziv trgovine") },
                    onValueChange = {
                        viewModel.onEvent(TrgovineEvent.EnteredNazivTrgovine(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.isNazivError
                )
                if (state.showError) {
                    Text(
                        text = "Naziv trgovine ne smije biti prazan!",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = MaterialTheme.typography.body2.fontSize
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Divider(thickness = 4.dp, modifier = Modifier.padding(4.dp))
                OutlinedTextField(
                    value = state.adresaTrgovine,
                    label = { Text(text = "Adresa trgovine") },
                    onValueChange = {
                        viewModel.onEvent(TrgovineEvent.EnteredAdresaTrgovine(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Divider(thickness = 4.dp, modifier = Modifier.padding(4.dp))
                Button(
                    onClick = { viewModel.onEvent(TrgovineEvent.DeleteTrgovina) },
                    modifier = Modifier.padding(horizontal = 4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White)
                ) {
                    Text(text = "Obriši")
                }
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