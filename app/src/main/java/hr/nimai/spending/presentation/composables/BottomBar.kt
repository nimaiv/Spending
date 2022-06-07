package hr.nimai.spending.presentation.composables

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import hr.nimai.spending.R
import hr.nimai.spending.presentation.NavGraphs
import hr.nimai.spending.presentation.destinations.*

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
                    Text(
                        text = stringResource(id = destination.label)
                    )
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
    Proizvodi(ProizvodiScreenDestination, Icons.Default.ShoppingBasket, R.string.proizvodi_screen),
    Trgovine(TrgovineScreenDestination, Icons.Default.Store, R.string.trgovine_screen),
    Potrosnja(PotrosnjaScreenDestination, Icons.Default.ShowChart, R.string.potrosnja_screen)
}