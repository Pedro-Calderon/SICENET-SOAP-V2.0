package com.example.marsphotos.ui.screens.loginScreen


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.marsphotos.model.Calificaciones
import com.example.marsphotos.model.getCalificacion
import com.example.marsphotos.navegacion.Route
import com.example.marsphotos.ui.screens.MarsViewModel

@Composable
fun Calificaciones(navController: NavHostController, viewModel: MarsViewModel) {
    val calificaciones by remember { viewModel.listaCalificaciones }
    val calificcionesSinCon by remember{viewModel.calificacionesState}

    Log.d("Calificaiones Screen","$calificcionesSinCon")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Calificaciones",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        items(calificaciones) { calificacion ->
            CalificacionItem(calificacion = calificacion)
        }
        items(calificcionesSinCon) { calificcionesSinCon ->
            CalificacionItemSinCon(calificacion = calificcionesSinCon)
        }

        item {
            Button(
                onClick = { navController.navigate(Route.LoginScreen.route) }
            ) {
                Text(
                    text = "Ir a calificación Final"
                )
            }
        }
    }
}

@Composable
fun CalificacionItem(calificacion: Calificaciones) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Materia: ${calificacion.Materia}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        for (i in 1..13) {
            val calificacionValue = calificacion.getCalificacion("C$i")
            if (calificacionValue != null) {
                Text(
                    text = "C$i: $calificacionValue",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Observación: ")
                }
                append("${calificacion.Observaciones}\n")
            },
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}


@Composable
fun CalificacionItemSinCon(calificacion: Calificaciones) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Materia: ${calificacion.Materia}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        for (i in 1..13) {
            val calificacionValue = calificacion.getCalificacion("C$i")
            if (calificacionValue != null) {
                Text(
                    text = "C$i: $calificacionValue",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Observación: ")
                }
                append("${calificacion.Observaciones}\n")
            },
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

