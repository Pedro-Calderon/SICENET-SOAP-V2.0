@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.example.marsphotos.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface DaoSicenet {
    @Query("SELECT * FROM Datosdelalumno ORDER BY Nombre ASC")
    fun getDatosdelalumnos(): LiveData<List<Item>>

    @Query("SELECT * FROM Calificaciones ORDER BY Matricula ASC")
    fun getCalificaciones(): LiveData<List<Item>>

    @Query("SELECT * FROM `Calificaciones Finales` ORDER BY Matricula ASC")
    fun getCalificacionesFinales(): LiveData<List<Item>>

    @Query("SELECT * FROM Kardex ORDER BY Matricula ASC")
    fun getKardex(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDatosdelalumno(datos: Item.Item0)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCalificaciones(calificaciones: Item.Item1)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCalificacionesFinales(calificacionesFinales: Item.Item2)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKardex(kardex: Item.Item3)
    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)
    // Puedes agregar métodos para actualizar y eliminar registros de cada entidad si lo necesitas

}

