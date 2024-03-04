

@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.marsphotos.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.marsphotos.DataBase.DatabaseSicenet
import com.example.marsphotos.DataBase.Item
import com.example.marsphotos.R
import com.example.marsphotos.ui.screens.HomeScreen
import com.example.marsphotos.ui.screens.MarsViewModel
import java.io.IOException

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsPhotosApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val marsViewModel: MarsViewModel = viewModel(factory = MarsViewModel.Factory)
    val alumnoProfile = marsViewModel.alumnoProfile

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MarsTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreen(
                marsUiState = marsViewModel.marsUiState,
                alumnoProfile = marsViewModel.alumnoProfile
            )
        }
    }
    val room: DatabaseSicenet =
        Room.databaseBuilder(LocalContext.current, DatabaseSicenet::class.java, "alumno").build()
    val connectivityManager = LocalContext.current.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo

    LaunchedEffect(marsViewModel.alumnoProfile) {
        if (alumnoProfile != null) {
            try {


                if (networkInfo != null && networkInfo.isConnected) {
                    room.DaoSicenet().insertarAlumno(
                        Item(
                            itemMatricula = alumnoProfile.matricula,
                            itemNombre = alumnoProfile.nombre,
                            itemCarrera = alumnoProfile.carrera,
                            itemSemestre = alumnoProfile.semActual,
                            fechaReins = alumnoProfile.fechaReins,
                            modEducativo = alumnoProfile.modEducativo,
                            adeudo = alumnoProfile.adeudo,
                            urlFoto = alumnoProfile.urlFoto,
                            adeudoDescripcion = alumnoProfile.adeudoDescripcion,
                            inscrito = alumnoProfile.inscrito,
                            estatus = alumnoProfile.estatus,
                            semActual = alumnoProfile.semActual,
                            cdtosAcumulados = alumnoProfile.cdtosAcumulados,
                            cdtosActuales = alumnoProfile.cdtosActuales,
                            especialidad = alumnoProfile.especialidad,
                            lineamiento = alumnoProfile.lineamiento
                        )
                    )
                } else {
                    // No hay conexión a Internet, manejar en consecuencia (mostrar mensaje, etc.)
                    Log.d("Error", "No hay conexión a Internet")
                }
            } catch (ioException: IOException) {
                // Manejar excepción de falta de conexión a Internet
                ioException.printStackTrace()
                Log.e("Error", "Error de conexión a Internet: ${ioException.message}")
            } catch (exception: Exception) {
                // Manejar otras excepciones
                exception.printStackTrace()
                Log.e("Error", "Error al insertar alumno en la base de datos: ${exception.message}")
            }
        } else {
            Log.d("Error", "No existe el alumno")
        }
    }

}
@Composable
fun MarsTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}
