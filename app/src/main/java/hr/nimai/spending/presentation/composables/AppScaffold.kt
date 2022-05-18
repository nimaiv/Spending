package hr.nimai.spending.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.ramcosta.composedestinations.spec.Route
import hr.nimai.spending.presentation.appCurrentDestinationAsState
import hr.nimai.spending.presentation.startAppDestination
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import hr.nimai.spending.presentation.destinations.Destination

@ExperimentalMaterialNavigationApi
@Composable
fun AppScaffold(
    startRoute: Route,
    navController: NavHostController,
    bottomBar: @Composable (Destination) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(16.dp)
    ) {
        Scaffold(
            bottomBar = { bottomBar(destination) },
            content = content
        )

    }
}
