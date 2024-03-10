package com.example.marsphotos.ui.screens

import LocatorAlumnos
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.marsphotos.DataBase.Acceso
import com.example.marsphotos.DataBase.CargaAcademica
import com.example.marsphotos.DataBase.DatabaseSicenet
import com.example.marsphotos.DataBase.DatosAlumno
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.Workers.AccesoLoginWorker
import com.example.marsphotos.Workers.AlmacenarDatosLocalWorker
import com.example.marsphotos.Workers.CalificacionesWorker
import com.example.marsphotos.Workers.CargaAcademicaWorker
import com.example.marsphotos.Workers.NetworkUtils
import com.example.marsphotos.data.LocatorCalificacion
import com.example.marsphotos.data.LocatorCargaAcademica
import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.data.ServiceLocator
import com.example.marsphotos.data.ServiceLocator.context
import com.example.marsphotos.model.AccesoLoginResult
import com.example.marsphotos.model.AlumnoAcademicoResponse
import com.example.marsphotos.model.Calificaciones
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.model.ModelocargaAcedemicarga
import com.example.marsphotos.model.SoapEnveloCalificacionUni
import com.example.marsphotos.model.SoapEnvelopeCarga
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.simpleframework.xml.core.Persister
import retrofit2.HttpException
import java.io.IOException


sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Error)
        private set
    var alumnoProfile: AlumnoAcademicoResponse? by mutableStateOf(null)
        private set

     var _accesoState by mutableStateOf<AccesoLoginResult?>(null)
    val accesoState: AccesoLoginResult? get() = _accesoState


    private var _listaCalificaciones: MutableState<List<Calificaciones>> = mutableStateOf(emptyList())
    val listaCalificaciones: State<List<Calificaciones>> = _listaCalificaciones


    private var _listaCarga: MutableState<List<ModelocargaAcedemicarga>> = mutableStateOf(emptyList())
    val listaCarga: State<List<ModelocargaAcedemicarga>> = _listaCarga


    var accesoSinConexion by mutableStateOf<Acceso?>(null)
        private set
    var datosAlumnoSinConexion by mutableStateOf<DatosAlumno?>(null)
        private set

    private val _calificacionesState = mutableStateOf<List<Calificaciones>>(emptyList())
    val calificacionesState: State<List<Calificaciones>> = _calificacionesState




    private val _cargaAcademica = mutableStateOf<List<ModelocargaAcedemicarga>>(emptyList())
    val cargaAcademicaState: State<List<ModelocargaAcedemicarga>> = _cargaAcademica





//s18120201, 5f_Wx%

    fun iniciarCalificacionesWorker() {
        // Crear una instancia de WorkRequest para el CalificacionesWorker
        val calificacionesWorkRequest = OneTimeWorkRequest.Builder(CalificacionesWorker::class.java)
            .build()

        // Programar la ejecución del trabajo
        WorkManager.getInstance(context)
            .enqueue(calificacionesWorkRequest)
    }

    fun iniciarCargaAcademicaWorker() {
        // Crear una instancia de WorkRequest para el CargaAcademicaWorker
        val cargaAcademicaWorkRequest = OneTimeWorkRequest.Builder(CargaAcademicaWorker::class.java)
            .build()

        // Programar la ejecución del trabajo
        WorkManager.getInstance(context)
            .enqueue(cargaAcademicaWorkRequest)
    }


    fun realizarAccesoLoginInBackground(matricula: String, password: String) {
        try {
            // Configura los datos de entrada para el Worker
            val inputData = workDataOf(
                "matricula" to matricula,
                "password" to password
            )

            // Configura las restricciones para el Worker
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            // Crea una instancia de AccesoLoginWorker con el trabajo único
            val accesoLoginWorkRequest =
                OneTimeWorkRequestBuilder<AccesoLoginWorker>()
                    .setInputData(inputData)
                    .setConstraints(constraints)
                    .build()

            // Obtiene una instancia de WorkManager y encola el trabajo
            WorkManager.getInstance(context).enqueue(accesoLoginWorkRequest)

            // Observa los resultados del Worker utilizando LiveData
            val workInfoLiveData = WorkManager.getInstance(context)
                .getWorkInfoByIdLiveData(accesoLoginWorkRequest.id)

            // Registra un observador para los resultados del Worker
            workInfoLiveData.observeForever { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            // El Worker ha tenido éxito, accede a los datos de salida
                            val acceso = workInfo.outputData.getString("acceso")
                            val matricula = workInfo.outputData.getString("matricula")
                            Log.d("Acceso viewmodel", "Sí hubo acceso para la matrícula: $matricula,$acceso")

                            if (acceso == "true") {
                                // Acceso exitoso, realiza las acciones necesarias

                                // Configura los datos de entrada para el siguiente Worker
                                val almacenarDatosInputData = workDataOf(
                                    "acceso" to acceso,
                                    "matricula" to matricula
                                )

                                // Crea una instancia de AlmacenarDatosLocalWorker
                                val almacenarDatosLocalWorkRequest =
                                    OneTimeWorkRequestBuilder<AlmacenarDatosLocalWorker>()
                                        .setInputData(almacenarDatosInputData)
                                        .setConstraints(constraints)
                                        .build()

                                // Encola el trabajo de AlmacenarDatosLocalWorker
                                WorkManager.getInstance(context).enqueue(almacenarDatosLocalWorkRequest)

                            } else {
                                // Acceso fallido, muestra un mensaje de error o realiza otras acciones necesarias
                                Log.d("Acceso Viewmodel", "No hubo acceso para la matrícula: $matricula")
                            }
                        }
                        WorkInfo.State.FAILED -> {
                            // El Worker ha fallado, maneja el error si es necesario
                            Log.e("Acceso viewmodel catch", "Error en AccesoLoginWorker: ${workInfo.outputData.getString("error")}")
                        }
                        // Puedes manejar otros estados del Worker según sea necesario
                        else -> {
                            Log.e("Acceso viewmodel catch 2", "Error en AccesoLoginWorker: ${workInfo.outputData.getString("error")}")
                        }
                    }
                }
            }

        } catch (e: Exception) {
            // Maneja cualquier excepción que pueda ocurrir al configurar el trabajo
            Log.e("Acceso", "Error en realizarAccesoLoginInBackground: $e")
            // Muestra un mensaje de error o realiza otras acciones necesarias
        }
    }

    fun realizarAccesoLogin( matricula:String, password:String) {
        val networkUtils = NetworkUtils(context) // 'this' representa el contexto de tu actividad o fragmento

        if (networkUtils.isNetworkAvailable()) {
            val requestBody = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <accesoLogin xmlns=\"http://tempuri.org/\">\n" +
                    "      <strMatricula>$matricula</strMatricula>\n" +
                    "      <strContrasenia>$password</strContrasenia>\n" +
                    "      <tipoUsuario>ALUMNO</tipoUsuario>\n" +
                    "    </accesoLogin>\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>"

            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = ServiceLocator.service.realizarAccesoLogin(
                        requestBody.toRequestBody("text/xml".toMediaTypeOrNull())
                    )

                    if (response.isSuccessful) {
                        val responseBodyString = response.body()?.string()
                        val startIndex = responseBodyString?.indexOf("{")
                        val endIndex = responseBodyString?.lastIndexOf("}")
                        val json = responseBodyString?.substring(startIndex ?: 0, endIndex?.plus(1) ?: 0)

                        val gson = Gson()
                        val accesoLoginResult = gson.fromJson(json, AccesoLoginResult::class.java)

                        withContext(Dispatchers.Main) {
                            // Actualizar el estado de la interfaz de usuario aquí
                            _accesoState = accesoLoginResult

                            if (accesoLoginResult.acceso == "true") {
                                getAlumnoAcademicoWithLineamiento()
                                realizarAccesoLoginInBackground(matricula,password)

                            } else {
                                // Manejar el caso de acceso fallido
                                // MarsUiState.Error, Log, o mostrar un mensaje de error
                                Log.d("Error: ", "ERROR: Credenciales inválidas")
                            }
                        }
                    } else {
                        // Manejar el caso de respuesta no exitosa
                        withContext(Dispatchers.Main) {
                            // MarsUiState.Error, Log, o mostrar un mensaje de error
                            Log.d("Error: ", "ERROR: ${response.errorBody().toString()}")
                        }
                    }
                } catch (e: IOException) {
                    // Manejar el caso de IOException
                    withContext(Dispatchers.Main) {
                        // MarsUiState.Error, Log, o mostrar un mensaje de error
                        Log.d("Error: ", "ERROR: Credenciales incorrectas")

                    }
                } catch (e: Exception) {
                    // Manejar otras excepciones inesperadas
                    withContext(Dispatchers.Main) {
                        // MarsUiState.Error, Log, o mostrar un mensaje de error
                        Log.e("Error: ", "ERROR inesperado: Credenciales incorrectas")
                    }
                }
            }


        } else {
            //codigo sin conexion
            LoginSinConexion()

        }



    }

    fun LoginSinConexion() {
        val database = DatabaseSicenet.invoke(ServiceLocator.context)
        viewModelScope.launch(Dispatchers.IO) {

            val accesoDesconexion = database.DaoSicenet().getAcceso()
            if (accesoDesconexion != null && accesoDesconexion.itemacceso == "true") {
                accesoSinConexion = accesoDesconexion
                val datosSinConexion=database.DaoSicenet().getDatosAlumno()
                if (datosSinConexion != null) {
                    datosAlumnoSinConexion=datosSinConexion
                }
                Log.d("AccesoSinConexion", "$datosSinConexion")


            } else {
                Log.d("AccesoSinConexion", "No hay datos de acceso")
            }
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



    fun getCalisinConexion(){
        val database = DatabaseSicenet.invoke(ServiceLocator.context)
        viewModelScope.launch {
            try {
                // Lógica para obtener las calificaciones de la base de datos
                val calificacionesEntities = database.DaoSicenet().getAllCalificaciones()
                Log.d("CaliSinCon 1","$calificacionesEntities")

                // Convierte las entidades a la clase de datos que necesitas mostrar en la UI
                val calificaciones = calificacionesEntities.map { entity ->
                    Calificaciones(
                       entity.Observaciones,
                        entity.C13,
                        entity.C12,
                        entity.C11,
                        entity.C10,
                        entity.C9,
                        entity.C8,
                        entity.C7,
                        entity.C6,
                        entity.C5,
                        entity.C4,
                        entity.C3,
                        entity.C2,
                        entity.C1,
                        entity.UnidadesActivas,
                        entity.Materia,
                        entity.Grupo

                    )
                }

                _calificacionesState.value = calificaciones
                Log.d("CaliSinCon","$calificaciones")
            } catch (e: Exception) {
                // Manejar las excepciones
                Log.e("MarsViewModel", "Error al obtener calificaciones de la base de datos: $e")
            }
        }
    }


    fun getCalifUnidadesByAlumnoResponse() {
        val networkUtils = NetworkUtils(context)


        if (networkUtils.isNetworkAvailable())
    {

        val requestBodyCal =
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <getCalifUnidadesByAlumno xmlns=\"http://tempuri.org/\" />\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>"

        val requestBodyCali = requestBodyCal.toRequestBody("text/xml".toMediaTypeOrNull())

        viewModelScope.launch(Dispatchers.IO) {
            marsUiState = MarsUiState.Loading

            try {
                val response = LocatorCalificacion.serviceCAL.cargaCalificacion(requestBodyCali)

                if (response.isSuccessful) {
                    val responseBodyString = response.body()?.string()

                    // Parsear la respuesta XML usando SimpleXML
                    val serializer = Persister()
                    val soapEnvelope =
                        serializer.read(SoapEnveloCalificacionUni::class.java, responseBodyString)
                    // Obtener el objeto específico de la respuesta
                    val califUnidadesResponse = soapEnvelope.body.getCalifUnidadesByAlumnoResponse

                    // Acceder a los datos dentro de la respuesta
                    val califUnidadesResult = califUnidadesResponse.getCalifUnidadesByAlumnoResult
                    Log.d("Calificaciones", califUnidadesResult)

                    // Deserializar la respuesta JSON utilizando kotlinx.serialization
                    val calificaciones: List<Calificaciones> =
                        Json.decodeFromString(califUnidadesResult.orEmpty())
                    _listaCalificaciones.value = calificaciones

                    // Acceder a los datos dentro de la lista de alumnos
                    for (calificacion in calificaciones) {
                        Log.d("Calificaciones", " ${calificacion}")
                    }
                    iniciarCalificacionesWorker()
                    MarsUiState.Success(califUnidadesResult)


                } else {
                    MarsUiState.Error
                }
            } catch (e: Exception) {
                MarsUiState.Error
                Log.d("Error Cali", "$e")
            }
        }
    }else
    {
        getCalisinConexion()
    }
    }


    fun getCargaAcademica(){

        val networkUtils = NetworkUtils(context) // 'this' representa el contexto de tu actividad o fragmento


        if (networkUtils.isNetworkAvailable()) {
            val requestBodyCarga =
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "  <soap:Body>\n" +
                        "    <getCargaAcademicaByAlumno xmlns=\"http://tempuri.org/\" />\n" +
                        "  </soap:Body>\n" +
                        "</soap:Envelope>"

            val requestBodyCarga1 = requestBodyCarga.toRequestBody("text/xml".toMediaTypeOrNull())

            viewModelScope.launch(Dispatchers.IO) {
                marsUiState = MarsUiState.Loading

                try {
                    val response =
                        LocatorCargaAcademica.serviceCarga.cargaacademica(requestBodyCarga1)

                    if (response.isSuccessful) {
                        val responseBodyString = response.body()?.string()

                        // Parsear la respuesta XML usando SimpleXML
                        val serializer = Persister()
                        val soapEnvelope =
                            serializer.read(SoapEnvelopeCarga::class.java, responseBodyString)
                        // Obtener el objeto específico de la respuesta
                        val cargaResponse = soapEnvelope.body.getCargaAcademicaByAlumnoResponse

                        // Acceder a los datos dentro de la respuesta
                        val cargaResult = cargaResponse.getCargaAcademicaByAlumnoResult
                        Log.d("Carga Academica", cargaResult)

                        // Deserializar la respuesta JSON utilizando kotlinx.serialization
                        val CargaAcademica: List<ModelocargaAcedemicarga> =
                            Json.decodeFromString(cargaResult.orEmpty())
                        _listaCarga.value = CargaAcademica

                        // Acceder a los datos dentro de la lista de alumnos
                        for (carga in CargaAcademica) {
                            Log.d("Carga academica", " ${carga}")

                        }
                        iniciarCargaAcademicaWorker()
                        MarsUiState.Success(cargaResult)


                    } else {
                        MarsUiState.Error
                        Log.d("Error carga if", "No fue succes")

                    }
                } catch (e: Exception) {
                    MarsUiState.Error
                    Log.d("Error carga", "$e")
                }
            }

        }else{
            getCargaSinCon()
        }
    }

    fun getCargaSinCon(){
        val database = DatabaseSicenet.invoke(ServiceLocator.context)
        viewModelScope.launch {
            try {
                // Lógica para obtener las calificaciones de la base de datos
                val cargaAcademica = database.DaoSicenet().getAllCarga()
                Log.d("CargaSinCon  1","$cargaAcademica")

                // Convierte las entidades a la clase de datos que necesitas mostrar en la UI
                val cargaAca = cargaAcademica.map { entity ->
                    ModelocargaAcedemicarga(
                        entity.Semipresencial,
                        entity.Observaciones,
                        entity.Docente,
                        entity.clvOficial,
                        entity.Sabado,
                        entity.Viernes,
                        entity.Jueves,
                        entity.Miercoles,
                        entity.Martes,
                        entity.Lunes,
                        entity.EstadoMateria,
                        entity.CreditosMateria,
                        entity.Materia,
                        entity.Grupo
                    )
                }

                _cargaAcademica.value = cargaAca
                Log.d("Carga sinCon","$cargaAca")
            } catch (e: Exception) {
                // Manejar las excepciones
                Log.e("MarsViewModel", "Error al obtener CargaAcademica de la base de datos: $e")
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