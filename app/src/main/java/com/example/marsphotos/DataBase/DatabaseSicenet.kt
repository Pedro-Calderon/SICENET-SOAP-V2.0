package com.example.marsphotos.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [DatosAlumno::class,Acceso::class],
    version = 1,
    exportSchema = false    )


abstract class DatabaseSicenet : RoomDatabase() {

    abstract fun DaoSicenet(): DaoSicenet

}