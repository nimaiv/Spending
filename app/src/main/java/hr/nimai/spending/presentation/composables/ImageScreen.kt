package hr.nimai.spending.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun ImageScreen(
    image: ByteArray,
    navigator: DestinationsNavigator
) {

    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            IconButton(
                onClick = { navigator.navigateUp() },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Natrag")
            }
        }
    ) { padding ->
        AsyncImage(
            model = ImageRequest.Builder(context).data(image).build(),
            contentDescription = "Slika",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}