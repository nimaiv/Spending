package hr.nimai.spending.presentation.add_racun

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun AddRacunScreen(
    ocrText: String,
    navigator: DestinationsNavigator,
    viewModel: AddRacunViewModel = hiltViewModel()
) {


    Surface {
        Text(text = ocrText)
    }
}