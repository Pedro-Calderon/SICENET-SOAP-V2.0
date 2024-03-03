    @file:Suppress("AndroidUnresolvedRoomSqlReference")

    package com.example.marsphotos.DataBase

    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.Query


    @Dao
    interface DaoSicenet {

        //Datos alumno
        @Query("Select*from Datosdelalumno")
        suspend fun getDatosAlumno():List<Item>
       @Insert
       suspend fun insertarAlumno(item:Item)




    }

