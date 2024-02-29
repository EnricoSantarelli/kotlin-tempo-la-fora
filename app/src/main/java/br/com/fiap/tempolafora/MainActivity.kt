package br.com.fiap.tempolafora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.tempolafora.cities.CitiesView
import br.com.fiap.tempolafora.cities.CitiesViewModel
import br.com.fiap.tempolafora.home.HomeView
import br.com.fiap.tempolafora.home.HomeViewModel
import br.com.fiap.tempolafora.info.InfoView
import br.com.fiap.tempolafora.info.InfoViewModel
import br.com.fiap.tempolafora.ui.theme.TempoLaForaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TempoLaForaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.white)
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home", builder = {
                        composable("home") {
                            HomeView(viewModel = HomeViewModel(), navController = navController)
                        }
                        composable("info/{cityName}",
                        ) {
                            val cityName = it.arguments?.getString("cityName")
                            InfoView(viewModel = InfoViewModel(), cityName, navController)
                        }
                        composable("cities",
                        ) {
                            CitiesView(viewModel = CitiesViewModel(), navController)
                        }
                    })
                }
            }
        }
    }
}
