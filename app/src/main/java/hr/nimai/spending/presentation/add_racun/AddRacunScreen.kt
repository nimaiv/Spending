package hr.nimai.spending.presentation.add_racun

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.nimai.spending.presentation.destinations.RacuniScreenDestination
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun AddRacunScreen(
    ocrText: String,
    navigator: DestinationsNavigator,
    viewModel: AddRacunViewModel = hiltViewModel()
) {
    val brojRacunaState = viewModel.brojRacuna.value
    val idTrgovineState = viewModel.idTrgovine.value
    val ukupanIznosState = viewModel.ukupanIznos.value
    val datumRacunaState = viewModel.datumRacuna.value
    val ocrTekstState = viewModel.ocrTekst.value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddRacunViewModel.UiEvent.SaveRacun -> {
                    navigator.navigate(RacuniScreenDestination)
                }
                is AddRacunViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddRacunEvent.SaveRacun)
                },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Spremi")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            hr.nimai.spending.presentation.add_racun.components.TextField(
                text = brojRacunaState.text,
                label = brojRacunaState.label,
                onValueChange = {
                    viewModel.onEvent(AddRacunEvent.EnteredBrojRacuna(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            hr.nimai.spending.presentation.add_racun.components.TextField(
                text = ukupanIznosState.text,
                label = ukupanIznosState.label,
                onValueChange = {
                    viewModel.onEvent(AddRacunEvent.EnteredUkupanIznos(it))
                },
                textStyle = MaterialTheme.typography.h5,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            hr.nimai.spending.presentation.add_racun.components.TextField(
                text = datumRacunaState.text,
                label = datumRacunaState.label,
                onValueChange = {
                    viewModel.onEvent(AddRacunEvent.EnteredDatumRacuna(it))
                },
                textStyle = MaterialTheme.typography.h5,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            hr.nimai.spending.presentation.add_racun.components.TextField(
                text = idTrgovineState.text,
                label = idTrgovineState.label,
                onValueChange = {
                    viewModel.onEvent(AddRacunEvent.EnteredTrgovina(it))
                },
                textStyle = MaterialTheme.typography.h5,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            hr.nimai.spending.presentation.add_racun.components.TextField(
                text = ocrTekstState.text,
                label = ocrTekstState.label,
                modifier = Modifier.fillMaxHeight(),
                onValueChange = {

                },
                textStyle = MaterialTheme.typography.body1,

            )
        }
    }
}