
package com.example.marsphotos.Workers

import LocatorAlumnos
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.marsphotos.DataBase.Acceso
import com.example.marsphotos.DataBase.DatabaseSicenet
import com.example.marsphotos.DataBase.DatosAlumno
import com.example.marsphotos.data.ServiceLocator.context
import com.example.marsphotos.model.AlumnoAcademicoResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

class AlmacenarDatosLocalWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            // Obtener los datos de acceso y perfil del resultado del primer Worker
            val acceso = inputData.getString("acceso")
            val matricula = inputData.getString("matricula")
            Log.e("AlmacenarDatosLocal", "error en :${acceso.isNullOrEmpty()},$matricula ")


            if (!acceso.isNullOrEmpty() && !matricula.isNullOrEmpty()) {


                // Obtener el perfil del alumno utilizando la matrícula
                val perfilResult = getPerfilFromApi()

                // Almacenar los datos localmente
                almacenarDatosLocalmente(perfilResult)

                return Result.success()
            }

            return Result.failure()
        } catch (e: Exception) {
            Log.e("AlmacenarDatosLocal", "Error en doWork: $e")
            return Result.failure()
        }
    }

    private suspend fun getPerfilFromApi(): AlumnoAcademicoResponse {
        return try {
            withContext(Dispatchers.IO) {
                val requestBody4 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "  <soap:Body>\n" +
                        "    <getAlumnoAcademicoWithLineamiento xmlns=\"http://tempuri.org/\" />\n" +
                        "  </soap:Body>\n" +
                        "</soap:Envelope>"
                val requestBody3 = requestBody4.toRequestBody("text/xml".toMediaTypeOrNull())

                // Realizar la solicitud de manera asíncrona
                val response = LocatorAlumnos.serviceAL.cargarPerfil(requestBody3)

                // Verificar la respuesta
                if (response.isSuccessful) {
                    val responseBodyString = response.body()?.string()
                    val startIndex = responseBodyString?.indexOf("{")
                    val endIndex = responseBodyString?.lastIndexOf("}")
                    val json = responseBodyString?.substring(startIndex ?: 0, endIndex?.plus(1) ?: 0)
                    val gson = Gson()
                    // Devuelve el objeto resultante del parsing
                    return@withContext gson.fromJson(json, AlumnoAcademicoResponse::class.java)
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    throw IOException("Error en la respuesta HTTP: ${response.code()}")
                }
            }
        } catch (e: IOException) {
            // Manejar excepciones de E/S
            throw IOException("Excepción durante la solicitud: $e")
        } catch (e: HttpException) {
            // Manejar excepciones HTTP
            throw IOException("Excepción HTTP: $e")
        }
    }



    private suspend fun almacenarDatosLocalmente(acceso1: AlumnoAcademicoResponse) {
        val accesos=DatosAlumno(
            1,
           acceso1.matricula,
            acceso1.nombre,
            acceso1.carrera,
            acceso1.lineamiento,
            acceso1.fechaReins,
            acceso1.modEducativo,
            acceso1.adeudo,
            acceso1.urlFoto,
            acceso1.adeudoDescripcion,
            acceso1.inscrito,
            acceso1.estatus,
            acceso1.cdtosAcumulados,
            acceso1.cdtosActuales,
            acceso1.especialidad,
            acceso1.lineamiento
        )
        val acceso2=Acceso(1,acceso1.matricula,"true")
        val db = Room.databaseBuilder(context, DatabaseSicenet::class.java, "SICENET").build()
        val userProfileDao = db.DaoSicenet()
        userProfileDao.insertarAlumno(accesos)
        userProfileDao.insertAcceso(acceso2)

    }
}