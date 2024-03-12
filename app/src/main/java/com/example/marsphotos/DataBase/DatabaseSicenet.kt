package com.example.marsphotos.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [DatosAlumno::class,Acceso::class,CalificacionesEntity::class, Califinal::class, CargaAcademica::class, Kardex::class, UltimaConexion::class],
    version = 1,
    exportSchema = false    )


abstract class DatabaseSicenet : RoomDatabase() {

    abstract fun DaoSicenet(): DaoSicenet
    companion object {
        fun invoke(context: Context): DatabaseSicenet {
            return Room.databaseBuilder(context, DatabaseSicenet::class.java, "SICENET")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}