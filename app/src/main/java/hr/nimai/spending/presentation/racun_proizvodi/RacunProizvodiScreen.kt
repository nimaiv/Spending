package hr.nimai.spending.presentation.racun_proizvodi

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun RacunProizvodiScreen(
    idRacuna: Int,
    navigator: DestinationsNavigator,
    viewModel: RacunProizvodiViewModel = hiltViewModel()
){

}