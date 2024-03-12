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
import com.example.marsphotos.model.CalificacionesResponse
import com.example.marsphotos.model.getCalificacion
import com.example.marsphotos.ui.screens.MarsViewModel


@Composable
fun CalificacionesFinales(navController: NavHostController, viewModel: MarsViewModel) {
    val lstcalificaciones by remember { viewModel.listaCalificacionesFinales }
    val lstFinalesOff by remember { viewModel.finalesState }


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

                items(lstcalificaciones) { calificacion ->
                    CalificacionFinalItem(calificacion = calificacion)
                }
            }
                ListFinales(ListFinales = lstcalificaciones)
            Spacer(modifier = Modifier.height(16.dp))
            BottomNavigationBar(navController = navController, viewModel=viewModel)

        }

    }

}

@Composable
fun ListFinales(ListFinales: List<CalificacionesResponse>){
    LazyColumn{
        items (ListFinales){ CalificacionesFItem ->
            CalificacionFinalItem(calificacion = CalificacionesFItem)
        }
    }


}

@Composable
fun CalificacionFinalItem(calificacion: CalificacionesResponse) {

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
                text = "${calificacion.calif}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "${calificacion.acred}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "${calificacion.grupo}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "${calificacion.materia}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "${calificacion.Observaciones}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

        }
    }
}
