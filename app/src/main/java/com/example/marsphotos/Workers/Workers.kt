package com.example.marsphotos.Workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.marsphotos.data.ServiceLocator
import com.example.marsphotos.model.AccesoLoginResult
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class AccesoLoginWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        try {
            val matricula = inputData.getString("matricula")
            val password = inputData.getString("password")

            if (!matricula.isNullOrEmpty() && !password.isNullOrEmpty()) {
                val resultado = realizarAccesoLogin(matricula, password)

                // Handle el resultado según sea necesario
                if (resultado.acceso == "true") {
                    // Acceso exitoso
                    Log.d("AccesoLoginWorker", "Acceso exitoso para la matrícula: $matricula")
                    Log.d("AccesoLoginWorker", "Resultado: $resultado")
                    val outputData = workDataOf(
                        "acceso" to resultado.acceso,
                        "matricula" to matricula
                    )
                    return Result.success(outputData)
                } else {
                    // Acceso fallido
                    Log.d("AccesoLoginWorker", "Acceso fallido para la matrícula: $matricula")
                    return Result.failure()
                }
            }

            return Result.failure()
        } catch (e: StringIndexOutOfBoundsException) {
            // Manejar la excepción específica
            // Puedes registrar el error, enviar un informe, o realizar otras acciones necesarias
            Log.e("AccesoLoginWorker", "Error en la operación: $e")

        }
        return Result.failure()


    }

    private fun realizarAccesoLogin(matricula: String, password: String): AccesoLoginResult {
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

        return try {
            runBlocking {
                val response = ServiceLocator.service.realizarAccesoLogin(requestBody2)
                if (response.isSuccessful) {
                    val responseBodyString = response.body()?.string()
                    val startIndex = responseBodyString?.indexOf("{")
                    val endIndex = responseBodyString?.lastIndexOf("}")
                    val json = responseBodyString?.substring(startIndex ?: 0, endIndex?.plus(1) ?: 0)
                    val gson = Gson()
                    val result = gson.fromJson(json, AccesoLoginResult::class.java)

                    Log.d("AccesoLoginWorker", "Respuesta exitosa: $result")
                    result
                } else {
                    Log.d("AccesoLoginWorker", "Respuesta fallida: ${response.errorBody().toString()}")
                    AccesoLoginResult(acceso = "false", matricula = "")
                }
            }
        } catch (e: IOException) {
            Log.d("AccesoLoginWorker", "Excepción durante la solicitud: $e")
            AccesoLoginResult(acceso = "false", matricula = "")
        }
    }
}
