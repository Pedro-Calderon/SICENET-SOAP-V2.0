package com.example.marsphotos.ui.screens

import LocatorAlumnos
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.data.ServiceLocator
import com.example.marsphotos.data.ServiceLocator.service
import com.example.marsphotos.model.AccesoLoginResult
import com.example.marsphotos.model.AlumnoAcademicoResponse
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.SICENETwService2
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    private var cookies: String? = null
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Error)
        private set
    var alumnoProfile: AlumnoAcademicoResponse? by mutableStateOf(null)
        private set

    fun realizarAccesoLogin() {
        val requestBody = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <accesoLogin xmlns=\"http://tempuri.org/\">\n" +
                "      <strMatricula>s18120201</strMatricula>\n" +
                "      <strContrasenia>5f_Wx%</strContrasenia>\n" +
                "      <tipoUsuario>ALUMNO</tipoUsuario>\n" +
                "    </accesoLogin>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
        val requestBody2= requestBody.toRequestBody(
            "text/xml".toMediaTypeOrNull())

        viewModelScope.launch(Dispatchers.IO) {
            val response = ServiceLocator.service.realizarAccesoLogin(requestBody2)
            if (response.isSuccessful) {



                val responseBodyString = response.body()?.string()
                // Parsear la respuesta XML a JSON
                val startIndex = responseBodyString?.indexOf("{")
                val endIndex = responseBodyString?.lastIndexOf("}")
                val json = responseBodyString?.substring(startIndex ?: 0, endIndex?.plus(1) ?: 0)
                val gson = Gson()
                val accesoLoginResult = gson.fromJson(json, AccesoLoginResult::class.java)
                Log.d("LOGIN JSON", "Response: $accesoLoginResult")
                Log.d("LOGIN XML", "Response: $responseBodyString")

             if (accesoLoginResult.acceso == ("true")){
                 getAlumnoAcademicoWithLineamiento()

                 }else{
                 MarsUiState.Error
                 Log.d("Error: ", "ERROR:Credenciales invalidas ") }


            }
                //(set-cookie, ASP.NET_SessionId=3eui3caqnaloyqndddzv5x21;
              //  Log.d("LOGIN JSON", "Response: $accesoLoginResult")
              //  Log.d("LOGIN XML", "Response: $responseBodyString")
            else {
                MarsUiState.Error
                Log.d("Error: ", "ERROR: ${response.errorBody().toString()}") }
        }


    }


   fun getAlumnoAcademicoWithLineamiento() {
       val requestBody4 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
               "  <soap:Body>\n" +
               "    <getAlumnoAcademicoWithLineamiento xmlns=\"http://tempuri.org/\" />\n" +
               "  </soap:Body>\n" +
               "</soap:Envelope>"
       val requestBody3 = requestBody4.toRequestBody(
           "text/xml".toMediaTypeOrNull())
     //  Log.d("Cookie LocatoAl", LocatorAlumnos.cok)

       viewModelScope.launch(Dispatchers.IO) {
           marsUiState = MarsUiState.Loading
           marsUiState = try {
               val response = LocatorAlumnos.serviceAL.cargarPerfil(requestBody3)
               if (response.isSuccessful) {
                   val responseBodyString = response.body()?.string()
                   val startIndex = responseBodyString?.indexOf("{")
                   val endIndex = responseBodyString?.lastIndexOf("}")
                   val json = responseBodyString?.substring(startIndex ?: 0, endIndex?.plus(1) ?: 0)
                   val gson = Gson()
                   val perfilResult = gson.fromJson(json, AlumnoAcademicoResponse::class.java)

                   alumnoProfile = perfilResult  // Actualizar el estado con el resultado del perfil del alumno

                   MarsUiState.Success(
                       buildString {
                           appendLine("Nombre: ${perfilResult.nombre}")
                           appendLine("Matrícula: ${perfilResult.matricula}")
                           appendLine("Fecha de Reinscripción: ${perfilResult.fechaReins}")
                           appendLine("Modelo Educativo: ${perfilResult.modEducativo}")
                           appendLine("Adeudo: ${perfilResult.adeudo}")
                           appendLine("URL de Foto: ${perfilResult.urlFoto}")
                           appendLine("Descripción de Adeudo: ${perfilResult.adeudoDescripcion}")
                           appendLine("Inscrito: ${perfilResult.inscrito}")
                           appendLine("Estatus: ${perfilResult.estatus}")
                           appendLine("Semestre Actual: ${perfilResult.semActual}")
                           appendLine("Créditos Acumulados: ${perfilResult.cdtosAcumulados}")
                           appendLine("Créditos Actuales: ${perfilResult.cdtosActuales}")
                           appendLine("Especialidad: ${perfilResult.especialidad}")
                           appendLine("Carrera: ${perfilResult.carrera}")
                           appendLine("Lineamiento: ${perfilResult.lineamiento}")
                       }
                   )
               } else {
                   MarsUiState.Error
               }
           } catch (e: IOException) {
               MarsUiState.Error
           } catch (e: HttpException) {
               MarsUiState.Error
           }
       }
   }

    /* fun getAlumnoAcademicoWithLineamiento() {
         val requestBody4 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                 "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                 "  <soap:Body>\n" +
                 "    <getAlumnoAcademicoWithLineamiento xmlns=\"http://tempuri.org/\" />\n" +
                 "  </soap:Body>\n" +
                 "</soap:Envelope>"
         val requestBody3= requestBody4.toRequestBody(
             "text/xml".toMediaTypeOrNull())

         viewModelScope.launch(Dispatchers.IO) {
             val response = LocatorAlumnos.serviceAL.cargarPerfil(requestBody3)
             if (response.isSuccessful) {
                 val responseBodyString = response.body()?.string()

                 Log.d("ALUMNO XML", "Response: $responseBodyString")
             } else {
                 Log.d("Error: ", "ERROR: ${response.errorBody().toString()}")
             }
         }
     }*/

    init {
        //getMarsPhotos()
       realizarAccesoLogin()
        //getAlumnoAcademicoWithLineamiento()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].

    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                val listResult = marsPhotosRepository.getMarsPhotos()
                MarsUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }
     */
    /**
     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }

}