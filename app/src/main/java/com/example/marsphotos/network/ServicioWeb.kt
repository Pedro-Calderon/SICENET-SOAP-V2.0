package com.example.marsphotos.network
import com.example.marsphotos.DataBase.DatosAlumno
import retrofit2.http.GET

interface ServicioWeb {
    @GET("/")
    suspend fun obtenerDatos(): List<DatosAlumno>
}

