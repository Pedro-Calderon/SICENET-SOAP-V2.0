package com.example.marsphotos

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marspho.LoginScreen
import com.example.marsphotos.navegacion.Route
import com.example.marsphotos.ui.screens.MarsViewModel
import com.example.marsphotos.ui.screens.loginScreen.Calificaciones
import com.example.marsphotos.ui.screens.loginScreen.CalificacionesFinales
import com.example.marsphotos.ui.screens.loginScreen.CargaAcademica
import com.example.marsphotos.ui.screens.loginScreen.Kardex
import com.example.marsphotos.ui.screens.loginScreen.PantallaDos
import com.example.marsphotos.ui.theme.MarsPhotosTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MarsPhotosTheme {
                val marsViewModel: MarsViewModel = viewModel(factory = MarsViewModel.Factory)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController= rememberNavController()
                    NavHost(navController = navController, startDestination = Route.LoginScreen.route){
                        composable(Route.LoginScreen.route){ LoginScreen(
                            viewModel = marsViewModel,
                            navController = navController
                        )}
                        composable(Route.PantallaDos.route){ PantallaDos(
                            navController = navController,
                            viewModel = marsViewModel
                        )
                        }
                        composable(Route.Calificaciones.route){ Calificaciones(
                            navController = navController,
                            viewModel = marsViewModel
                        )
                        }
                        composable(Route.CalificacionesFinales.route){ CalificacionesFinales(
                            navController = navController ,
                            viewModel = marsViewModel
                        )
                        }
                        composable(Route.CargaAcademica.route){ CargaAcademica(
                            navController = navController,
                            viewModel = marsViewModel
                        )
                        }
                        composable(Route.Kardex.route){ Kardex(
                            navController = navController,
                            viewModel = marsViewModel
                        )
                        }
                    }

                }
            }
        }
    }
}
