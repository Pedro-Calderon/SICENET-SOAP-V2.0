package com.example.marsphotos.ui.screens.loginScreen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.marsphotos.navegacion.Route

@Composable
fun PantallaDos(navController: NavHostController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pantalla 2",
                fontSize = 20.sp,
                color = Color.White
            )

            Button(
                onClick = { navController.navigate(Route.LoginScreen.route) }
            ) {
                Text(
                    text = "Ir a pantalla 3"
                )
            }
        }
    }
}
