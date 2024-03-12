package com.example.marsphotos.Workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.marsphotos.DataBase.DatabaseSicenet
import com.example.marsphotos.data.LocatorFinal
import com.example.marsphotos.model.CalificacionFResponse
import com.example.marsphotos.model.CalificacionesResponse
import com.example.marsphotos.model.SoapEnvelopeRequest
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.simpleframework.xml.core.Persister
import retrofit2.Response

class CalificacionFinalWoker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val modeloE = inputData.getString("modeloe")
            // Realizar la solicitud de calificaciones finales
            val calificacionesResult = modeloE?.let { realizarSolicitudCalificacionesFinales(it) }

            Log.d("workerCalificacionFinal", "$calificacionesResult")

            // Obtener la lista de calificaciones finales
            val responseBodyString = calificacionesResult?.body()?.string()
            Log.d("workerCalifica 2", "$responseBodyString")

            // Parsear la respuesta XML usando SimpleML
            val serializer1 = Persister()
            val soapEnvelope = serializer1.read(SoapEnvelopeRequest::class.java, responseBodyString)

            // Obtener el objeto especifico de la respuesta
            val finalResponse = soapEnvelope.body.getAllCalifFinalByAlumnosResponse

            // Acceder a los datos dentro de la respuesta
            val FinalResult = finalResponse.getAllCalifFinalByAlumnosResult
            Log.d("workerCalificacionFinal 3", FinalResult)

            // Deserializar la respuesta Json
            val gson = Gson()
            val FinalesListResponse: CalificacionFResponse =
                gson.fromJson(FinalResult, CalificacionFResponse::class.java)

            // Obtener la lista de calificaciones finales
            val FinalesList: List<CalificacionesResponse> = FinalesListResponse.lstFinal
            val databaseSicenet = DatabaseSicenet.invoke(applicationContext)

            if (databaseSicenet.DaoSicenet().getCalificacionFinal() > 0) {
                databaseSicenet.DaoSicenet().clearCALIFICACIONFINAL()
            }
            // Recorrer la lista y enviar cada calificacion por separado
            for (CalificacionesFinales4 in FinalesList) {
                val inputData1 =
                    workDataOf("CalificacionItem" to Json.encodeToString(CalificacionesFinales4))
                // Crear una instancia de WorkRequest para el AlmacenarCalificacionesFinalWorker
                val almacenarCalificacionesFinalWorkRequest =
                    OneTimeWorkRequest.Builder(AlmacenaCalificacionesFinalesWorker::class.java)
                        .setInputData(inputData1)
                        .build()

                // Programar la ejecución del trabajo
                WorkManager.getInstance(applicationContext)
                    .enqueue(almacenarCalificacionesFinalWorkRequest)
            }

            return Result.success()
        } catch (e: Exception) {
            // Manejar las excepciones
            Log.e("CalificacionesFinalesWorker", "Error durante la ejecución del Worker: $e")
            return Result.failure()
        }
    }

    private suspend fun realizarSolicitudCalificacionesFinales(modeloE: String): Response<ResponseBody> {
        // Configurar y realizar la solicitud a través de Retrofit
        val requestBodyCal =
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <getAllCalifFinalByAlumnos xmlns=\"http://tempuri.org/\">\n" +
                    "      <bytModEducativo>$modeloE</bytModEducativo>\n" +
                    "    </getAllCalifFinalByAlumnos>\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>"

        val requestBodyCali = requestBodyCal.toRequestBody("text/xml".toMediaTypeOrNull())
        return LocatorFinal.serviceFinal.cargaFinal(requestBodyCali)
    }

}
