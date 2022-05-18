package hr.nimai.spending.presentation.composables

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import hr.nimai.spending.R
import hr.nimai.spending.presentation.destinations.DirectionDestination
import hr.nimai.spending.presentation.destinations.RacuniScreenDestination
import hr.nimai.spending.presentation.NavGraphs
import hr.nimai.spending.presentation.destinations.RacunScanScreenDestination

@Composable
fun BottomBar(
    navHostController: NavHostController
) {
    BottomNavigation {
        BottomBarItem.values().forEach { destination ->
            val isCurrentDestOnBackStack = navHostController.isRouteOnBackStack(destination.direction)
            BottomNavigationItem(
                selected = isCurrentDestOnBackStack,
                onClick = {
                    if (isCurrentDestOnBackStack) {
                        navHostController.popBackStack(destination.direction, false)
                        return@BottomNavigationItem
                    }

                    navHostController.navigate(destination.direction) {
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                },
                icon = {
                       Icon(
                           destination.icon,
                           contentDescription = stringResource(id = destination.label)
                       )
                },
                label = {
                    Text(text = stringResource(id = destination.label))
                }
            )
        }
    }
}

enum class BottomBarItem(
    val direction: DirectionDestination,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Racuni(RacuniScreenDestination, Icons.Default.Receipt, R.string.racuni_screen),
    Skeniraj(RacunScanScreenDestination, Icons.Default.DocumentScanner, R.string.scan_screen),
}