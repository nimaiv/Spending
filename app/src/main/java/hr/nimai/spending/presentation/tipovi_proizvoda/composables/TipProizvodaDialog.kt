package hr.nimai.spending.presentation.tipovi_proizvoda.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hr.nimai.spending.presentation.tipovi_proizvoda.TipoviProizvodaEvent
import hr.nimai.spending.presentation.tipovi_proizvoda.TipoviProizvodaViewModel

@Composable
fun TipProizvodaDialog(
    viewModel: TipoviProizvodaViewModel
) {
    val state = viewModel.state.value

    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(TipoviProizvodaEvent.DismissDialog)
        },
        title = {
            if (state.isEdit) {
                Text("Uredi tip proizvoda")
            } else {
                Text("Novi tip proizvoda")
            }

        },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = state.nazivTipaProizvoda,
                    label = { Text(text = "Naziv") },
                    onValueChange = {
                        viewModel.onEvent(TipoviProizvodaEvent.EnteredNaziv(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                if (state.isEdit) {
                    Button(
                        onClick = {
                            viewModel.onEvent(TipoviProizvodaEvent.DeleteTipProizvoda)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(text = "Obriši")
                    }
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
                        viewModel.onEvent(TipoviProizvodaEvent.DismissDialog)
                    },
                ) {
                    Text("Odustani")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.onEvent(TipoviProizvodaEvent.SaveTipProizvoda)
                    }
                ) {
                    Text("Spremi")
                }
            }
        }
    )
}