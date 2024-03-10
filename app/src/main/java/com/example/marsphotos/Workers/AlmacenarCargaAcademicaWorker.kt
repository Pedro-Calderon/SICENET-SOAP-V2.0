package com.example.marsphotos.Workers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.marsphotos.DataBase.CalificacionesEntity
import com.example.marsphotos.DataBase.CargaAcademica
import com.example.marsphotos.DataBase.DatabaseSicenet
import com.example.marsphotos.model.Calificaciones
import com.example.marsphotos.model.ModelocargaAcedemicarga
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class AlmacenarCargaAcademicaWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    @SuppressLint("SuspiciousIndentation")
    override fun doWork(): Result {
        try {
            // Obtener la lista de calificaciones del inputData
            val cargaListJson = inputData.getString("cargalist")
            Log.d("WorkerAlmacena Carga 1","$cargaListJson")

            val gson = Gson()

            // Utilizamos un TypeToken para indicar que queremos deserializar la lista de objetos Calificaciones
            val listType = object : TypeToken<List<ModelocargaAcedemicarga>>() {}.type
            Log.d("WorkerAlmacenaCarga 2","$listType")
            val cargaList: List<ModelocargaAcedemicarga> = gson.fromJson(cargaListJson, listType)
            Log.d("WorkerAlmacenaCarga 2","$cargaList")

            for (carga in cargaList){
                Log.d("WorkerAlmacenaCarga 2","$carga")

            }
            // Acceder a la instancia de la base de datos
            val database = DatabaseSicenet.invoke(applicationContext)

            runBlocking(Dispatchers.IO) {
                almacenarCargaAcademicaEnDB(cargaList,database)
            }
            return Result.success()
        } catch (e: Exception) {
            // Manejar las excepciones
            Log.e("AlmacenarCargaAcademicaWorker", "Error durante la ejecución del Worker: $e")
            return Result.failure()
        }
    }

    private suspend fun almacenarCargaAcademicaEnDB(cargaList: List<ModelocargaAcedemicarga>, database: DatabaseSicenet) {
        val dao = database.DaoSicenet()
        Log.d("WorkerAlmacenaCarga 3","$dao")
        var id=1;
        if (dao.getCarga()>0){
            dao.clearCarga()
        }
        for (carga in cargaList) {


            dao.insertCarga(
                CargaAcademica(
                    id=id,
                    carga.Semipresencial,
                    carga.Observaciones,
                    carga.Docente,
                    carga.clvOficial,
                    carga.Sabado,
                    carga.Viernes,
                    carga.Jueves,
                    carga.Miercoles,
                    carga.Martes,
                    carga.Lunes,
                    carga.EstadoMateria,
                    carga.CreditosMateria,
                    carga.Materia,
                    carga.Grupo
            )
            )
            id++
        }
    }
}
