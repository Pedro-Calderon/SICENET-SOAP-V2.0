package com.example.marsphotos.ui.screens.loginScreen

import android.util.Log
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
import androidx.compose.runtime.rememberUpdatedState
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
import com.example.marsphotos.model.KardexItem
import com.example.marsphotos.ui.screens.MarsViewModel
@Composable
fun Kardex(navController: NavHostController, viewModel: MarsViewModel) {
    val kardexList by remember { viewModel.listaKardex }
    val kardexListOffline by remember { viewModel.kardexState }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kardex",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )


                KardexList(kardexList = kardexList)
                KardexList(kardexList = kardexListOffline)



            Log.d("KardexScreen","$kardexListOffline")
            Spacer(modifier = Modifier.height(16.dp))
            BottomNavigationBar(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun KardexList(kardexList: List<KardexItem>) {
    LazyColumn {
        items(kardexList) { kardexItem ->
            KardexItemCard(kardex = kardexItem)
        }
        items(kardexList) { kardexItem ->
            KardexItemCard(kardex = kardexItem)
        }
    }
}

@Composable
fun KardexItemCard(kardex: KardexItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                text = "CVE: ${kardex.ClvMat}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Oficial: ${kardex.ClvOfiMat}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Materia: ${kardex.Materia}",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 18.sp, color = Color.Black)) {
                        append("Nivel de Desempeño: ")
                    }


                    if (kardex.Calif != null) {
                        val nivelDesempeno = when {
                            kardex.Calif < 70 -> "NA"
                            kardex.Calif in 70..79 -> "Suficiente"
                            kardex.Calif in 80..85 -> "Bueno"
                            kardex.Calif in 86..95 -> "Notable"
                            kardex.Calif in 96..100 -> "Excelente"
                            else -> "Desconocido"
                        }

                        withStyle(style = SpanStyle(fontSize = 18.sp, color = Color.Black)) {
                            append(" ($nivelDesempeno)")
                        }
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Créditos: ${kardex.Cdts}",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            kardex.Calif?.let {
                Text(
                    text = "Calificación: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            kardex.Acred?.let {
                Text(
                    text = "Acreditación: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Text(
                text = "Ordinario: ",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            kardex.S1.let {
                Text(
                    text = "Semestre: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            kardex.P1.let {
                Text(
                    text = "Periodo: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            kardex.A1.let {
                Text(
                    text = "Año: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            kardex.S2?.let {
                Text(
                    text = "Repeticion: ",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            kardex.S2?.let {
                Text(
                    text = "Semestre: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            kardex.P2?.let {
                Text(
                    text = "Periodo: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            kardex.A2?.let {
                Text(
                    text = "Año: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            kardex.S3?.let {
                Text(
                    text = "Especial: ",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            kardex.S3?.let {
                Text(
                    text = "Semestre: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            kardex.P3?.let {
                Text(
                    text = "Periodo: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            kardex.A3?.let {
                Text(
                    text = "Año: $it",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

        }
    }
}

