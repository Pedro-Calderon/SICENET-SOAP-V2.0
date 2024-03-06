package com.example.marsphotos.Workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.marsphotos.data.LocatorCalificacion
import com.example.marsphotos.model.Calificaciones
import com.example.marsphotos.model.SoapEnveloCalificacionUni
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.simpleframework.xml.core.Persister
import retrofit2.Response

class CalificacionesWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            // Realizar la solicitud de calificaciones
            val calificacionesResult = realizarSolicitudCalificaciones()
            Log.d("workerCalificacion","$calificacionesResult")
            // Obtener la lista de calificaciones
            val calificacionesList = obtenerCalificaciones(calificacionesResult)

            // Crear un objeto Data para incluir la lista de calificaciones
            val inputData = workDataOf("calificacionesList" to Json.encodeToString(calificacionesList))

            // Crear una instancia de WorkRequest para el AlmacenarCalificacionesWorker
            val almacenarCalificacionesWorkRequest = OneTimeWorkRequest.Builder(AlmacenarCalificacionesWorker::class.java)
                .setInputData(inputData)
                .build()

            // Programar la ejecución del trabajo
            WorkManager.getInstance(applicationContext)
                .enqueue(almacenarCalificacionesWorkRequest)

            return Result.success()
        } catch (e: Exception) {
            // Manejar las excepciones
            Log.e("CalificacionesWorker", "Error durante la ejecución del Worker: $e")
            return Result.failure()
        }
    }

    private suspend fun realizarSolicitudCalificaciones(): Response<ResponseBody> {
        // Configurar y realizar la solicitud a través de Retrofit
        val requestBodyCal =
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <getCalifUnidadesByAlumno xmlns=\"http://tempuri.org/\" />\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>"

        val requestBodyCali = requestBodyCal.toRequestBody("text/xml".toMediaTypeOrNull())
        return LocatorCalificacion.serviceCAL.cargaCalificacion(requestBodyCali)
    }

    private fun obtenerCalificaciones(response: Response<ResponseBody>): List<Calificaciones> {

            val responseBodyString = response.body()?.string()

            // Parsear la respuesta XML usando SimpleXML
            val serializer = Persister()
            val soapEnvelope = serializer.read(SoapEnveloCalificacionUni::class.java, responseBodyString)
            // Obtener el objeto específico de la respuesta
            val califUnidadesResponse = soapEnvelope.body.getCalifUnidadesByAlumnoResponse

            // Acceder a los datos dentro de la respuesta
            val califUnidadesResult = califUnidadesResponse.getCalifUnidadesByAlumnoResult
            Log.d("Calificaciones", califUnidadesResult)

            // Deserializar la respuesta JSON utilizando kotlinx.serialization
            val calificaciones: List<Calificaciones> = Json.decodeFromString(califUnidadesResult.orEmpty())

            // Acceder a los datos dentro de la lista de alumnos
            for (calificacion in calificaciones) {
                Log.d("Calificaciones", " ${calificacion}")
            }


return calificaciones


    }

}
