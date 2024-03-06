package com.example.marsphotos.ui.screens.loginScreen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.marsphotos.navegacion.Route
import com.example.marsphotos.ui.screens.MarsViewModel

@Composable
fun PantallaDos(navController: NavHostController, viewModel: MarsViewModel) {
    val alumno = rememberUpdatedState(newValue = viewModel.alumnoProfile)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Datos del Alumno",
                fontSize = 30.sp,
                color = Color.Black
            )


            alumno.value?.let { alumno ->
                // Muestra los datos según sea necesario
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Matrícula: ")
                        }
                        append("${alumno.matricula}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Nombre: ")
                        }
                        append("${alumno.nombre}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Fecha de Reinscripción: ")
                        }
                        append("${alumno.fechaReins}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Modelo Educativo: ")
                        }
                        append("${alumno.modEducativo}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Adeudo: ")
                        }
                        append("${alumno.adeudo}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("URL de Foto: ")
                        }
                        append("${alumno.urlFoto}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Descripción de Adeudo: ")
                        }
                        append("${alumno.adeudoDescripcion}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Inscrito: ")
                        }
                        append("${alumno.inscrito}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Estatus: ")
                        }
                        append("${alumno.estatus}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Semestre Actual: ")
                        }
                        append("${alumno.semActual}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Créditos Acumulados: ")
                        }
                        append("${alumno.cdtosAcumulados}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Créditos Actuales: ")
                        }
                        append("${alumno.cdtosActuales}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Especialidad: ")
                        }
                        append("${alumno.especialidad}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Carrera: ")
                        }
                        append("${alumno.carrera}\n")

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Lineamiento: ")
                        }
                        append("${alumno.lineamiento}\n")
                    },
                    fontSize = 16.sp,
                    color = Color.Black
                )

            }

            Button(
                onClick = { navController.navigate(Route.Calificaciones.route) }
            ) {
                Text(
                    text = "Ir a Calificacione"
                )
            }
        }
    }
}
