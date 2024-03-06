package com.example.marsphotos.Workers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.marsphotos.DataBase.CalificacionesEntity
import com.example.marsphotos.DataBase.DatabaseSicenet
import com.example.marsphotos.model.Calificaciones
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class AlmacenarCalificacionesWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    @SuppressLint("SuspiciousIndentation")
    override fun doWork(): Result {
        try {
            // Obtener la lista de calificaciones del inputData
            val calificacionesListJson = inputData.getString("calificacionesList")
            Log.d("WorkerAlmacena 1","$calificacionesListJson")

           val gson = Gson()

            // Utilizamos un TypeToken para indicar que queremos deserializar la lista de objetos Calificaciones
           val listType = object : TypeToken<List<Calificaciones>>() {}.type
              Log.d("WorkerAlmacena 2","$listType")
           val calificacionesList: List<Calificaciones> = gson.fromJson(calificacionesListJson, listType)
            Log.d("WorkerAlmacena 2","$calificacionesList")

          //  for (cali in calificacionesList){
              //  Log.d("WorkerAlmacena 2","$cali")

           // }
            // Acceder a la instancia de la base de datos
            val database = DatabaseSicenet.invoke(applicationContext)

            runBlocking(Dispatchers.IO) {
               almacenarCalificacionesEnDB(calificacionesList, database)
            }
            return Result.success()
        } catch (e: Exception) {
            // Manejar las excepciones
            Log.e("AlmacenarCalificacionesWorker", "Error durante la ejecución del Worker: $e")
            return Result.failure()
        }
    }

    private suspend fun almacenarCalificacionesEnDB(calificacionesList: List<Calificaciones>, database: DatabaseSicenet) {
        val dao = database.DaoSicenet()
        Log.d("WorkerAlmacena 3","$dao")
        var id=1;
        if (dao.getCalificacionesCount()>0){
            dao.clearCalificacionesTable()
        }
        for (calificacion in calificacionesList) {


            dao.insertCalificacion(CalificacionesEntity(
                id=id,
                calificacion.Materia,
                calificacion.Observaciones,
                calificacion.C1,
                calificacion.C2,
                calificacion.C3,
                calificacion.C4,
                calificacion.C5,
                calificacion.C6,
                calificacion.C7,
                calificacion.C8,
                calificacion.C9,
                calificacion.C10,
                calificacion.C11,
                calificacion.C12,
                calificacion.C13,
                calificacion.UnidadesActivas,
                calificacion.Grupo
            ))
            id++
        }
    }
}
