package com.example.marsphotos.Workers

import LocatorAlumnos
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.marsphotos.data.ServiceLocator
import com.example.marsphotos.model.AccesoLoginResult
import com.example.marsphotos.model.AlumnoAcademicoResponse
import com.example.marsphotos.ui.screens.MarsUiState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
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

                    // Llamada a getAlumnoAcademicoWithLineamiento después de un acceso exitoso
                    runBlocking {
                        getAlumnoAcademicoWithLineamiento()


                    }

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

    private suspend fun getAlumnoAcademicoWithLineamiento(): MarsUiState {
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
                    val perfilResult = gson.fromJson(json, AlumnoAcademicoResponse::class.java)

                    Log.d("Alumno work", "$perfilResult")

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
                    // Crear el estado de error en caso de respuesta no exitosa
                    MarsUiState.Error
                }
            }
        } catch (e: IOException) {
            // Crear el estado de error en caso de excepción de E/S
            MarsUiState.Error
        } catch (e: HttpException) {
            // Crear el estado de error en caso de excepción HTTP
            MarsUiState.Error
        }
    }

    }

