    @file:Suppress("AndroidUnresolvedRoomSqlReference")

    package com.example.marsphotos.DataBase

    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.Query


    @Dao
    interface DaoSicenet {

        //Datos alumn
        @Query("Select*from Datosdelalumno")
        suspend fun getDatosAlumno():List<DatosAlumno>
       @Insert
       suspend fun insertarAlumno(item:DatosAlumno)


        @Query("Select*from Acceso")
        suspend fun getAcceso():List<Acceso>
        @Insert
        suspend fun insertAcceso(item1:Acceso)



    }

