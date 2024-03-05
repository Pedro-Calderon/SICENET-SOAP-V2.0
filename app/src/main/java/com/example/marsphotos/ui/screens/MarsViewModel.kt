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
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.Workers.AccesoLoginWorker
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.data.ServiceLocator
import com.example.marsphotos.data.ServiceLocator.context
import com.example.marsphotos.model.AccesoLoginResult
import com.example.marsphotos.model.AlumnoAcademicoResponse
import com.example.marsphotos.model.MarsPhoto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Error)
        private set
    var alumnoProfile: AlumnoAcademicoResponse? by mutableStateOf(null)
        private set
    private var _accesoState by mutableStateOf<AccesoLoginResult?>(null)
    val accesoState: AccesoLoginResult? get() = _accesoState


//s18120201, 5f_Wx%
fun realizarAccesoLoginInBackground(matricula: String, password: String) {
    val inputData = workDataOf(
        "matricula" to matricula,
        "password" to password
    )

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val accesoLoginWorkRequest =
        OneTimeWorkRequestBuilder<AccesoLoginWorker>()
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

    WorkManager.getInstance(context).enqueue(accesoLoginWorkRequest)

    // Observa los resultados del Worker utilizando LiveData
    val workInfoLiveData = WorkManager.getInstance(context)
        .getWorkInfoByIdLiveData(accesoLoginWorkRequest.id)

    workInfoLiveData.observeForever { workInfo ->
        if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
            // El Worker ha tenido éxito, puedes acceder a los datos de salida
            val acceso = workInfo.outputData.getString("acceso")
            val matricula = workInfo.outputData.getString("matricula")

            if (acceso == "true") {
                // Acceso exitoso, realiza las acciones necesarias (navegar a otra pantalla, etc.)
                Log.d("Acceso", "Sí hubo acceso para la matrícula: $matricula")



                // Realiza otras acciones necesarias aquí
            } else {
                // Acceso fallido, puedes mostrar un mensaje de error si es necesario
                Log.d("Acceso", "No hubo acceso para la matrícula: $matricula")
                // Muestra un mensaje de error o realiza otras acciones necesarias aquí
            }
        }
    }
}

    fun realizarAccesoLogin( matricula:String, password:String) {
        val requestBody = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <accesoLogin xmlns=\"http://tempuri.org/\">\n" +
                "      <strMatricula>$matricula</strMatricula>\n" +
                "      <strContrasenia>$password</strContrasenia>\n" +
                "      <tipoUsuario>ALUMNO</tipoUsuario>\n" +
                "    </accesoLogin>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>"
        val requestBody2= requestBody.toRequestBody(
            "text/xml".toMediaTypeOrNull())
        try {
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

                    _accesoState = accesoLoginResult

                    Log.d("LOGIN JSON", "Response: $accesoLoginResult")


                    if (accesoLoginResult.acceso == ("true")){
                        getAlumnoAcademicoWithLineamiento()

                    }else{
                        MarsUiState.Error
                        Log.d("Error: ", "ERROR:Credenciales invalidas ")
                        _accesoState = AccesoLoginResult(acceso = "false", matricula="")

                    }



                }else {
                    MarsUiState.Error
                    Log.d("Error: ", "ERROR: ${response.errorBody().toString()}")
                    _accesoState = AccesoLoginResult(acceso = "false", matricula="")


                }

            }
        }catch (e:IOException){
            MarsUiState.Error
            Log.d("Error: ", "ERROR: Credenciales incorretas")

            _accesoState = AccesoLoginResult(acceso = "false", matricula="")


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

                   alumnoProfile = perfilResult
                    Log.d("Alumno","$perfilResult")


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


    init {
        getMarsPhotos()

    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
        MarsUiState.Success(
            "Success: $alumnoProfile?.nombre"
        ) } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }




    }
    /**  * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
    x*
    fun getMarsPhotos() {
            viewModelScope.launch {
            marsUiState = MarsUiState.Loading
       try {

           realizarAccesoLogin()

            } catch (e: IOException) {
            MarsUiState.Error
            } catch (e: HttpException) {
            MarsUiState.Error
            }
            }
    }

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