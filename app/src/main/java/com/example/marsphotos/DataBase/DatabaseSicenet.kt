package com.example.marsphotos.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [Item::class],
    version = 1)

abstract class DatabaseSicenet : RoomDatabase() {

    abstract fun DaoSicenet(): DaoSicenet
/*
    companion object{
        @Volatile
        private var INSTANCE: DatabaseSicenet? = null
        fun getDatabase(context: Context): DatabaseSicenet{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,DatabaseSicenet::class.java,
                    "DatabaseSicenet"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

 */
}