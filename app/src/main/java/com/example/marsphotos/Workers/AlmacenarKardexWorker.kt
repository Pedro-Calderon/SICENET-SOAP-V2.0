package com.example.marsphotos.Workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.marsphotos.DataBase.CargaAcademica
import com.example.marsphotos.DataBase.DatabaseSicenet
import com.example.marsphotos.DataBase.Kardex
import com.example.marsphotos.DataBase.RepositoryLocal
import com.example.marsphotos.model.KardexItem
import com.example.marsphotos.model.ModelocargaAcedemicarga
import kotlinx.serialization.json.Json

class AlmacenarKardexWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            // Obtener la referencia a la base de datos
            val databaseSicenet = DatabaseSicenet.invoke(applicationContext)
            // Obtén el KardexItem de la inputData
            val kardexItemString = inputData.getString("kardexItem")
            val kardexItem = Json.decodeFromString<KardexItem>(kardexItemString!!)


            almacenarKardexEnDB(kardexItem,databaseSicenet)

            return Result.success()
        } catch (e: Exception) {
            // Manejar las excepciones
            Log.e("AlmacenarKardexWorker", "Error durante la ejecución del Worker: $e")
            return Result.failure()
        }
    }
    private suspend fun almacenarKardexEnDB(kardexItem: KardexItem, database: DatabaseSicenet) {
        val dao = database.DaoSicenet()


        dao.insertKArdex(
            Kardex(
                S3 = kardexItem.S3,
                P3 = kardexItem.P3,
                A3 = kardexItem.A3,
                ClvMat = kardexItem.ClvMat,
                ClvOfiMat = kardexItem.ClvOfiMat,
                Materia = kardexItem.Materia,
                Cdts = kardexItem.Cdts.toString(),
                Calif = kardexItem.Calif.toString(),
                Acred = kardexItem.Acred,
                S1 = kardexItem.S1,
                P1 = kardexItem.P1,
                A1 = kardexItem.A1,
                S2 = kardexItem.S2,
                P2 = kardexItem.P2,
                A2 = kardexItem.A2
            )
        )

    }
}
