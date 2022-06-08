package hr.nimai.spending.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import hr.nimai.spending.presentation.composables.AppScaffold
import hr.nimai.spending.presentation.composables.BottomBar
import hr.nimai.spending.ui.theme.SpendingTheme


@OptIn(ExperimentalMaterialNavigationApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendingTheme {
                val navController = rememberNavController()
                val startRoute = NavGraphs.root.startRoute
                AppScaffold(
                    startRoute = startRoute,
                    navController = navController,
                    bottomBar = { BottomBar(navHostController = navController) }
                ) {
                    DestinationsNavHost(
                        navController = navController,
                        navGraph = NavGraphs.root,
                        modifier = Modifier.padding(it),
                        startRoute = startRoute
                    )
                }
            }
        }
    }
}

