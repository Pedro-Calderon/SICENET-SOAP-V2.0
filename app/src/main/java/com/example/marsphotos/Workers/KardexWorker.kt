package com.example.marsphotos.Workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.marsphotos.DataBase.DatabaseSicenet
import com.example.marsphotos.data.LocatorCargaAcademica
import com.example.marsphotos.data.LocatorKardex
import com.example.marsphotos.model.KardexItem
import com.example.marsphotos.model.KardexResponse
import com.example.marsphotos.model.ModelocargaAcedemicarga
import com.example.marsphotos.model.SoapEnvelopeCarga
import com.example.marsphotos.model.SoapEnvelopeKardex
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.simpleframework.xml.core.Persister
import retrofit2.Response

class KardexWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val lineamiento = inputData.getString("lineamiento")

            // Realizar la solicitud de calificaciones
            val cargaResult = lineamiento?.let { realizarSolicitudKardex(it) }

            Log.d("WorkerKardex","$cargaResult")
            // Obtener la lista de calificaciones

            val responseBodyString = cargaResult?.body()?.string()
            Log.d("WorkerKardex 2","$responseBodyString")

            // Parsear la respuesta XML usando SimpleXML
            val serializer = Persister()
            val soapEnvelope =
                serializer.read(SoapEnvelopeKardex::class.java, responseBodyString)

            // Obtener el objeto específico de la respuesta
            val kardexResponse = soapEnvelope.body.getAllKardexConPromedioByAlumnoResponse

            // Acceder a los datos dentro de la respuesta
            val kardexResult = kardexResponse.getAllKardexConPromedioByAlumnoResult
            Log.d("Kardex Worker 3", kardexResult)

            // Deserializar la respuesta JSON utilizando kotlinx.serialization
            val gson = Gson()
            val kardexListResponse: KardexResponse =
                gson.fromJson(kardexResult, KardexResponse::class.java)

            /// Obtén la lista de kardex
            val kardexList: List<KardexItem> = kardexListResponse.lstKardex
            val databaseSicenet = DatabaseSicenet.invoke(applicationContext)

            if (databaseSicenet.DaoSicenet().getKardex()>0){
                databaseSicenet.DaoSicenet().cleaKardex()
            }
            // Recorre la lista y envía cada KardexItem por separado
            for (kardex in kardexList) {
                val inputData = workDataOf("kardexItem" to Json.encodeToString(kardex))

                // Crea una instancia de WorkRequest para el siguiente Worker
                val almacenarKardexWorker = OneTimeWorkRequest.Builder(AlmacenarKardexWorker::class.java)
                    .setInputData(inputData)
                    .build()

                // Programa la ejecución del trabajo
                WorkManager.getInstance(applicationContext)
                    .enqueue(almacenarKardexWorker)
            }




            return Result.success()
        } catch (e: Exception) {
            // Manejar las excepciones
            Log.e("KardexWorker", "Error durante la ejecución del Worker: $e")
            return Result.failure()
        }
    }

    private suspend fun realizarSolicitudKardex(lineamiento:String): Response<ResponseBody> {
        // Configurar y realizar la solicitud a través de Retrofit
        val requestBodyKardex =
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <getAllKardexConPromedioByAlumno xmlns=\"http://tempuri.org/\">\n" +
                    "      <aluLineamiento>$lineamiento</aluLineamiento>\n" +
                    "    </getAllKardexConPromedioByAlumno>\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>"

        val requestBodyCarga1 = requestBodyKardex.toRequestBody("text/xml".toMediaTypeOrNull())
        return LocatorKardex.serviceKardex.cargarKardex(requestBodyCarga1)
    }

}
