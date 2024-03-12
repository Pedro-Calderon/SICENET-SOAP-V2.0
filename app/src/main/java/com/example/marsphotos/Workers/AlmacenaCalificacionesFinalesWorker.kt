package com.example.marsphotos.Workers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

import com.example.marsphotos.DataBase.Califinal
import com.example.marsphotos.DataBase.DatabaseSicenet

import com.example.marsphotos.model.CalificacionesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class AlmacenaCalificacionesFinalesWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {
        try {
            //Obtener la referencia ala base de datos
            val databaseSicenet = DatabaseSicenet.invoke(applicationContext)
            // Obtener la lista de calificaciones finales del inputData
            val calificacionesListJson = inputData.getString("calificacionesList")
            val calificacionesList = Json.decodeFromString<CalificacionesResponse>(calificacionesListJson!!)
            Log.d("WorkerAlmacenaFinal 1", "$calificacionesListJson")

            almacenarCalificacionesFinalesEnDB(calificacionesList, databaseSicenet)

            return Result.success()
        } catch (e: Exception) {
            // Manejar las excepciones
            Log.e("AlmacenarCalificacionesFinalWorker", "Error durante la ejecución del Worker: $e")
            return Result.failure()
        }
    }

    private suspend fun almacenarCalificacionesFinalesEnDB(calificacionesList: CalificacionesResponse, database: DatabaseSicenet) {
        val dao = database.DaoSicenet()

        dao.insertCalificacionFinal(
            Califinal(
                calif = calificacionesList.calif,
                acred = calificacionesList.acred,
                grupo = calificacionesList.grupo,
                materia = calificacionesList.materia,
                Observacion = calificacionesList.Observaciones
            )
        )
    }
}
