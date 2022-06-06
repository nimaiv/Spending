package hr.nimai.spending.presentation.racuni

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.nimai.spending.presentation.destinations.RacunProizvodiScreenDestination
import hr.nimai.spending.presentation.destinations.RacunScanScreenDestination
import hr.nimai.spending.presentation.racuni.components.OrderSection
import hr.nimai.spending.presentation.racuni.components.RacunItem
import hr.nimai.spending.presentation.scan.ScanEvent
import hr.nimai.spending.presentation.scan.components.CameraView

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalPermissionsApi::class)
@Destination(start = true)
@Composable
fun RacuniScreen(
    navigator: DestinationsNavigator,
    viewModel: RacuniViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    val multiplePermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    )


    if (multiplePermissionsState.allPermissionsGranted) {
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigator.navigate(RacunScanScreenDestination)
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.DocumentScanner,
                        contentDescription = "Skeniraj račun"
                    )
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Računi",
                        style = MaterialTheme.typography.h4
                    )
                    IconButton(
                        onClick = {
                            viewModel.onEvent(RacuniEvent.ToggleOrderSection)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort, contentDescription = "Sortiraj"
                        )
                    }
                }
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        racunOrder = state.racunOrder,
                        onOrderChange = {
                            viewModel.onEvent(RacuniEvent.Order(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.racuni) { racun ->
                        RacunItem(
                            datum = racun.datum_racuna,
                            iznos = racun.ukupan_iznos_racuna,
                            naziv_trgovine = "Test1",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigator.navigate(RacunProizvodiScreenDestination(racun.id_racuna))
                                },
                        )
                    }
                }
            }
        }
    } else {
        Column {
            val textToShow = "Potrebna su dopuštenja za pravilnu uporabu aplikacije. Molim dodajte ih."

            Text(textToShow)
            Button(
                onClick = {
                    multiplePermissionsState.launchMultiplePermissionRequest()
                }) {
                Text("Dodaj dopuštenja")
            }
        }

    }
}