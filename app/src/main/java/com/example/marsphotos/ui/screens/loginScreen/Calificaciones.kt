package com.example.marsphotos.ui.screens.loginScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.example.marsphotos.ui.screens.loginScreen.BottomNavigationBar

@Composable
fun Calificaciones(navController: NavHostController, viewModel: MarsViewModel) {
    val calificaciones by remember { viewModel.listaCalificaciones }
    val calificcionesSinCon by remember{viewModel.calificacionesState}

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calificaciones",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(calificaciones) { calificacion ->
                    CalificacionItem(calificacion = calificacion)
                }
                items(calificcionesSinCon) { calificacion ->
                    CalificacionItemSinCon(calificacion = calificacion)
                }
            }


            /*Button(
                onClick = { navController.navigate(Route.LoginScreen.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "Ir a calificación Final"
                )
            }*/
            Spacer(modifier = Modifier.height(16.dp))
            BottomNavigationBar(navController = navController, viewModel=viewModel)

        }

    }

}

@Composable
fun CalificacionItem(calificacion: Calificaciones) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        //elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "${calificacion.Materia}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            for (i in 1..13) {
                val calificacionValue = calificacion.getCalificacion("C$i")
                if (calificacionValue != null) {
                    Text(
                        text = "C$i: $calificacionValue",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
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
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Composable
fun CalificacionItemSinCon(calificacion: Calificaciones) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        //elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "${calificacion.Materia}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            for (i in 1..13) {
                val calificacionValue = calificacion.getCalificacion("C$i")
                if (calificacionValue != null) {
                    Text(
                        text = "C$i: $calificacionValue",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
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
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
