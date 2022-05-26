package hr.nimai.spending.presentation.add_racun

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
                hint = brojRacunaState.hint,
                onValueChange = {
                    viewModel.onEvent(AddRacunEvent.EnteredBrojRacuna(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddRacunEvent.ChangeBrojRacunaFocus(it))
                },
                isHintVisible = brojRacunaState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            hr.nimai.spending.presentation.add_racun.components.TextField(
                text = ukupanIznosState.text,
                hint = ukupanIznosState.hint,
                onValueChange = {
                    viewModel.onEvent(AddRacunEvent.EnteredUkupanIznos(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddRacunEvent.ChangeUkupanIznosFocus(it))
                },
                isHintVisible = ukupanIznosState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            hr.nimai.spending.presentation.add_racun.components.TextField(
                text = datumRacunaState.text,
                hint = datumRacunaState.hint,
                onValueChange = {
                    viewModel.onEvent(AddRacunEvent.EnteredDatumRacuna(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddRacunEvent.ChangeDatumRacunaFocus(it))
                },
                isHintVisible = datumRacunaState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            hr.nimai.spending.presentation.add_racun.components.TextField(
                text = idTrgovineState.text,
                hint = idTrgovineState.hint,
                onValueChange = {
                    viewModel.onEvent(AddRacunEvent.EnteredTrgovina(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddRacunEvent.ChangeTrgovinaFocus(it))
                },
                isHintVisible = idTrgovineState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            hr.nimai.spending.presentation.add_racun.components.TextField(
                text = ocrTekstState.text,
                hint = ocrTekstState.hint,
                onValueChange = {

                },
                onFocusChange = {

                },
                isHintVisible = ocrTekstState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight(),

            )
        }
    }
}