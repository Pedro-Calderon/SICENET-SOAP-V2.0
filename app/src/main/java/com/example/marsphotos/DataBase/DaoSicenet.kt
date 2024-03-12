@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.example.marsphotos.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marsphotos.model.KardexItem
import com.example.marsphotos.model.ModeloFecha


@Dao
interface DaoSicenet {

    //Datos alumn
    @Query("Select*from Datosdelalumno")
    suspend fun getDatosAlumno():DatosAlumno?

    @Insert
    suspend fun insertarAlumno(datosAlumno: DatosAlumno)
    @Query("SELECT COUNT(*) FROM Datosdelalumno")
    suspend fun getDatosAlumnoCount(): Int
    @Query("DELETE FROM Datosdelalumno")
    suspend fun clearDatosAlumno()

    //acceso
    @Query("SELECT * FROM Acceso WHERE id = 1")
    suspend fun getAcceso(): Acceso?

    @Insert
    suspend fun insertAcceso(acceso:Acceso)
    @Query("SELECT COUNT(*) FROM Acceso")
    suspend fun getAccesoCount(): Int
    @Query("DELETE FROM Acceso")
    suspend fun clearAcceso()
    //Calificaciones
    @Insert
    suspend fun insertCalificacion(calificacion: CalificacionesEntity)
    @Query("SELECT COUNT(*) FROM Calificaciones")
    suspend fun getCalificacionesCount(): Int
    @Query("DELETE FROM Calificaciones")
    suspend fun clearCalificacionesTable()
    @Query("SELECT * FROM Calificaciones")
    suspend fun getAllCalificaciones(): List<CalificacionesEntity>
    // Calificaciones finales
    @Insert
    suspend fun insertCalificacionFinal(calificacionF: Califinal)
    @Query("SELECT COUNT(*) FROM Calificacion_final")
    suspend fun  getCalificacionFinal():Int
    @Query ("DELETE FROM Calificacion_final")
    suspend fun clearCALIFICACIONFINAL()
    @Query("SELECT * FROM Calificacion_final")
    suspend fun getAllCalificacionesFinal(): List<Califinal>

    //CargaAcademica

    @Insert
    suspend fun insertCarga(carga: CargaAcademica)
    @Query("SELECT COUNT(*) FROM CargaAcademica")
    suspend fun getCarga(): Int
    @Query("DELETE FROM CargaAcademica")
    suspend fun clearCarga()
    @Query("SELECT * FROM CargaAcademica")
    suspend fun getAllCarga(): List<CargaAcademica>
    //Kardex
    @Insert
    suspend fun insertKArdex(Kardex: Kardex)
    @Query("SELECT COUNT(*) FROM Kardex")
    suspend fun getKardex(): Int
    @Query("DELETE FROM Kardex")
    suspend fun cleaKardex()
    @Query("SELECT * FROM Kardex")
    suspend fun getAllKardex(): List<Kardex>

    @Insert
    suspend fun insertKardexList(kardexList: List<Kardex>)

    @Insert
    suspend fun insertarFecha(fecha: UltimaConexion)

    @Query("SELECT * FROM Fecha")
    suspend fun obtenerFecha(): List<UltimaConexion>

    @Query("SELECT COUNT(*) FROM Fecha")
    suspend fun getFechas(): Int
    @Query("DELETE FROM Fecha")
    suspend fun cleaFechas()

}

