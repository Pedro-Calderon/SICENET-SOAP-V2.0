package com.example.marsphotos.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [DatosAlumno::class,Acceso::class,CalificacionesEntity::class],
    version = 2,
    exportSchema = false    )


abstract class DatabaseSicenet : RoomDatabase() {

    abstract fun DaoSicenet(): DaoSicenet
    companion object {
        fun invoke(context: Context): DatabaseSicenet {
            return Room.databaseBuilder(context, DatabaseSicenet::class.java, "SICENET")
                .fallbackToDestructiveMigration()  // Migración destructiva
                .build()
        }
    }
}