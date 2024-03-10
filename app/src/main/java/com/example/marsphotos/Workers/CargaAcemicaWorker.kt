package com.example.marsphotos.Workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.marsphotos.data.LocatorCargaAcademica
import com.example.marsphotos.model.ModelocargaAcedemicarga
import com.example.marsphotos.model.SoapEnvelopeCarga
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.simpleframework.xml.core.Persister
import retrofit2.Response

class CargaAcademicaWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            // Realizar la solicitud de calificaciones
            val cargaResult = realizarSolicitudCargaAcademica()
            Log.d("workerCalificacion","$cargaResult")
            // Obtener la lista de calificaciones
            val cargalist = obtenerCarga(cargaResult)

            // Crear un objeto Data para incluir la lista de calificaciones
            val inputData = workDataOf("cargalist" to Json.encodeToString(cargalist))

            // Crear una instancia de WorkRequest para el AlmacenarCalificacionesWorker
            val almacenarCargaAcademicaWorker = OneTimeWorkRequest.Builder(AlmacenarCargaAcademicaWorker::class.java)
                .setInputData(inputData)
                .build()

            // Programar la ejecución del trabajo
            WorkManager.getInstance(applicationContext)
                .enqueue(almacenarCargaAcademicaWorker)

            return Result.success()
        } catch (e: Exception) {
            // Manejar las excepciones
            Log.e("CargaAcademicaWorker", "Error durante la ejecución del Worker: $e")
            return Result.failure()
        }
    }

    private suspend fun realizarSolicitudCargaAcademica(): Response<ResponseBody> {
        // Configurar y realizar la solicitud a través de Retrofit
        val requestBodyCarga =
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <getCargaAcademicaByAlumno xmlns=\"http://tempuri.org/\" />\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>"

        val requestBodyCarga1 = requestBodyCarga.toRequestBody("text/xml".toMediaTypeOrNull())
        return LocatorCargaAcademica.serviceCarga.cargaacademica(requestBodyCarga1)
    }
    private fun obtenerCarga(response: Response<ResponseBody>): List<ModelocargaAcedemicarga> {

        val responseBodyString = response.body()?.string()

        // Parsear la respuesta XML usando SimpleXML
        val serializer = Persister()
        val soapEnvelope = serializer.read(SoapEnvelopeCarga::class.java, responseBodyString)

        // Obtener el objeto específico de la respuesta
        val cargaResponse = soapEnvelope.body.getCargaAcademicaByAlumnoResponse

        // Acceder a los datos dentro de la respuesta
        val cargaResultString = cargaResponse.getCargaAcademicaByAlumnoResult
        Log.d("Carga Academica Worker", cargaResultString)

        // Deserializar la respuesta JSON utilizando kotlinx.serialization
        val cargaAcademica: List<ModelocargaAcedemicarga> = Json.decodeFromString(cargaResultString.orEmpty())


        return cargaAcademica


    }
}
