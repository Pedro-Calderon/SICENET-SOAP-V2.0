package com.example.marsphotos.network
import com.example.marsphotos.DataBase.Item
import retrofit2.http.GET

interface ServicioWeb {
    @GET("/")
    suspend fun obtenerDatos(): List<Item>
}

