package hr.nimai.spending.presentation.racun_proizvodi

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Transformations.map
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import hr.nimai.spending.domain.model.Racun
import hr.nimai.spending.presentation.add_racun.AddRacunEvent
import hr.nimai.spending.presentation.add_racun.AddRacunViewModel
import hr.nimai.spending.presentation.destinations.BarcodeScanScreenDestination
import hr.nimai.spending.presentation.destinations.ImageScreenDestination
import hr.nimai.spending.presentation.destinations.SelectProizvodScreenDestination
import hr.nimai.spending.presentation.racun_proizvodi.composables.EditKupnjaDialog
import hr.nimai.spending.presentation.racun_proizvodi.composables.EditRacunDialog
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Destination
fun RacunProizvodiScreen(
    idRacuna: Int,
    navigator: DestinationsNavigator,
    resultRecipientProizvod: ResultRecipient<SelectProizvodScreenDestination, Int>,
    resultRecipientBarcode: ResultRecipient<BarcodeScanScreenDestination, String>,
    viewModel: RacunProizvodiViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RacunProizvodiViewModel.UiEvent.LoadSlika -> {
                    viewModel.onEvent(RacunProizvodiEvent.LoadSlika(context))
                }
                is RacunProizvodiViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is RacunProizvodiViewModel.UiEvent.SaveRacun -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(RacunProizvodiEvent.DismissDialog)
                }
                is RacunProizvodiViewModel.UiEvent.DeleteRacun -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    navigator.navigateUp()
                }
                is RacunProizvodiViewModel.UiEvent.ScanBarcode -> {
                    navigator.navigate(BarcodeScanScreenDestination)
                }
            }
        }
    }

    resultRecipientProizvod.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                viewModel.onEvent(RacunProizvodiEvent.AddExistingProizvod(result.value))
            }
        }
    }

    resultRecipientBarcode.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                viewModel.onEvent(RacunProizvodiEvent.GetDataWithBarcode(result.value))
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navigator.navigateUp() },
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Natrag")
                }
                Text(
                    text = "Pregled računa",
                    style = MaterialTheme.typography.h6
                )
                Button(
                    onClick = { viewModel.onEvent(RacunProizvodiEvent.ShowEditRacunDialog) },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(text = "Uredi")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)) {
            Divider(thickness = 2.dp, modifier = Modifier.padding(vertical = 2.dp))
            Row() {
                Text(
                    text = "Broj računa: ",
                    modifier = Modifier.weight(1f)
                )
                Text(text = state.racun?.broj_racuna ?:"")
            }
            Row() {
                Text(
                    text = "Datum računa: ",
                    modifier = Modifier.weight(1f)
                )
                Text(text = state.racun?.datum_racuna ?:"")
            }
            Row() {
                Text(
                    text = "Trgovina: ",
                    modifier = Modifier.weight(1f)
                )
                if (state.nazivTrgovine.isBlank()) {
                    Text(text = "-")
                } else {
                    Text(
                        text = state.nazivTrgovine,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(2f)
                    )
                }
            }
            Row() {
                Text(
                    text = "Ukupan iznos računa: ",
                    modifier = Modifier.weight(1f)
                )
                Text(text = "${state.racun?.ukupan_iznos_racuna.toString()}kn")
            }
            Row() {
                Button(
                    onClick = {
                        viewModel.onEvent(RacunProizvodiEvent.ShowOCRTextDialog)
                    }
                ) {
                    Text(
                        text = "Očitani tekst računa"
                    )
                }
            }
            Card(
                modifier = Modifier
                    .size(108.dp)
                    .padding(8.dp),
                elevation = 2.dp,
                onClick = {
                    if (state.slika != null) {
                        navigator.navigate(ImageScreenDestination(state.slika))
                    }
                }
            ) {
                if (state.slika != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(state.slika).build(),
                        contentDescription = "Slika",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = hr.nimai.spending.R.drawable.ic_outline_image_not_supported_24),
                        contentDescription = "Slika",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Proizvodi:",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.h6
                )
                IconButton(
                    onClick = { viewModel.onEvent(RacunProizvodiEvent.ShowNewKupnjaDialog) }
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Dodaj")
                }
            }
            LazyColumn() {
                items(state.kupnjeProizvoda) { kupnjaProizvoda ->
                    Divider(thickness = 2.dp, modifier = Modifier.padding(vertical = 4.dp))
                    Surface(
                        onClick = {
                            viewModel.onEvent(RacunProizvodiEvent.ShowEditKupnjaDialog(kupnjaProizvoda))
                        },
                        color = MaterialTheme.colors.background
                    ) {
                        Column() {
                            Row() {
                                Text(
                                    text = kupnjaProizvoda.naziv_proizvoda,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.body2
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "${kupnjaProizvoda.cijena}kn",
                                    modifier = Modifier.weight(2f)
                                )
                                Spacer(modifier = Modifier.padding(2.dp))
                                Text(
                                    text = " * ${kupnjaProizvoda.kolicina}",
                                    modifier = Modifier.weight(2f)
                                )
                                Spacer(modifier = Modifier.padding(2.dp))
                                Text(
                                    text = " = ${kupnjaProizvoda.cijena * kupnjaProizvoda.kolicina}kn"
                                )
                            }
                        }
                    }
                }
            }
        }
        if (state.isOCRTextDialogShown) {
            Dialog(
                onDismissRequest = { viewModel.onEvent(RacunProizvodiEvent.DismissDialog) },
            ) {
                Card {
                    Column(
                        modifier = Modifier
                            .padding(8.dp),
                    ) {
                        Text(
                            text = state.racun?.ocr_tekst ?: "",
                            modifier = Modifier
                                .height(300.dp)
                                .verticalScroll(rememberScrollState())
                        )
                        TextButton(onClick = { viewModel.onEvent(RacunProizvodiEvent.DismissDialog) }) {
                            Text(text = "Zatvori")
                        }
                    }
                }
            }
        }

        if (state.isEditRacunDialogShown) {
            EditRacunDialog(
                viewModel = viewModel
            )
        }
        if (state.isEditKupnjaDialogShown) {
            EditKupnjaDialog(viewModel = viewModel) {
                navigator.navigate(SelectProizvodScreenDestination(
                    state.kupnjeProizvoda.map { it.id_proizvoda }.toHashSet().toIntArray())
                )
            }
        }
    }
}