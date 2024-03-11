package com.example.marsphotos.ui.screens.loginScreen



import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.marsphotos.navegacion.Route
import com.example.marsphotos.ui.screens.MarsViewModel

@Composable
fun PantallaDos(navController: NavHostController, viewModel: MarsViewModel) {
    val alumno = rememberUpdatedState(newValue = viewModel.alumnoProfile)
    val alumnoSinCon= rememberUpdatedState(newValue = viewModel.datosAlumnoSinConexion)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Datos del Alumno",
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            alumno.value?.let { alumno ->
                // Muestra los datos según sea necesario
                Surface (
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column (modifier = Modifier.padding(16.dp)) {
                        DatosItem("Matricula:", alumno.matricula)
                        DatosItem("Nombre:", alumno.nombre)
                        DatosItem("Fecha de Reinscripción:", alumno.fechaReins)
                        DatosItem("Modelo Educativo:", alumno.modEducativo.toString())
                        DatosItem("Adeudo:", alumno.adeudo.toString())
                        DatosItem("URL de Foto:", alumno.urlFoto)
                        DatosItem("Descripción de Adeudo:", alumno.adeudoDescripcion)
                        DatosItem("Inscrito:", alumno.inscrito.toString())
                        DatosItem("Estatus:", alumno.estatus)
                        DatosItem("Semestre Actual:", alumno.semActual.toString())
                        DatosItem("Créditos Acumulados:", alumno.cdtosAcumulados.toString())
                        DatosItem("Créditos Actuales:", alumno.cdtosActuales.toString())
                        DatosItem("Especialidad:", alumno.especialidad)
                        DatosItem("Carrera:", alumno.carrera)
                        DatosItem("Lineamiento:", alumno.lineamiento.toString())
                    }
                }
            }
            alumnoSinCon.value?.let { alumnoSinCon ->
                // Muestra los datos según sea necesario
                Surface (
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column (modifier = Modifier.padding(16.dp)) {
                        DatosItem("Matricula:", alumnoSinCon.itemMatricula)
                        DatosItem("Nombre:", alumnoSinCon.itemNombre)
                        DatosItem("Fecha de Reinscripción:", alumnoSinCon.fechaReins)
                        DatosItem("Modelo Educativo:", alumnoSinCon.modEducativo.toString())
                        DatosItem("Adeudo:", alumnoSinCon.adeudo.toString())
                        DatosItem("URL de Foto:", alumnoSinCon.urlFoto)
                        DatosItem("Descripción de Adeudo:", alumnoSinCon.adeudoDescripcion)
                        DatosItem("Inscrito:", alumnoSinCon.inscrito.toString())
                        DatosItem("Estatus:", alumnoSinCon.estatus)
                        DatosItem("Semestre Actual:", alumnoSinCon.itemSemestre.toString())
                        DatosItem("Créditos Acumulados:", alumnoSinCon.cdtosAcumulados.toString())
                        DatosItem("Créditos Actuales:", alumnoSinCon.cdtosActuales.toString())
                        DatosItem("Especialidad:", alumnoSinCon.especialidad)
                        DatosItem("Carrera:", alumnoSinCon.itemCarrera)
                        DatosItem("Lineamiento:", alumnoSinCon.lineamiento.toString())
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            BottomNavigationBar(navController = navController, viewModel=viewModel)
        }
    }
}

@Composable
fun DatosItem(label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.width(150.dp)
        )
        Text(
            text = value,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, viewModel:MarsViewModel) {
    val alumno = rememberUpdatedState(newValue = viewModel.alumnoProfile)

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            selected = false,
            onClick = {
                navController.navigate(Route.PantallaDos.route)

            },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Profile")}
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                navController.navigate(Route.Calificaciones.route)
                viewModel.getCalifUnidadesByAlumnoResponse()
                //viewModel.iniciarCalificacionesWorker()
            },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Calificaiones ") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                viewModel.getCargaAcademica()

                navController.navigate(Route.CargaAcademica.route)

            },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Carga")}
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                viewModel.getKardex()
                //viewModel.iniciarProcesoKardex(alumno.value?.lineamiento.toString())
                navController.navigate(Route.Kardex.route)
            },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Kardex")}
        )
    }
}

