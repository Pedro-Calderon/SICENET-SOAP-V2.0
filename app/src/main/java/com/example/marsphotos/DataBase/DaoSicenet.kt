    @file:Suppress("AndroidUnresolvedRoomSqlReference")

    package com.example.marsphotos.DataBase

    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.Query


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

        //CargaAcademica

        @Insert
        suspend fun insertCarga(carga: CargaAcademica)
        @Query("SELECT COUNT(*) FROM CargaAcademica")
        suspend fun getCarga(): Int
        @Query("DELETE FROM CargaAcademica")
        suspend fun clearCarga()
        @Query("SELECT * FROM CargaAcademica")
        suspend fun getAllCarga(): List<CargaAcademica>
        //
    }

