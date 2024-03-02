package com.example.marsphotos
import com.example.marsphotos.DataBase.Item
import retrofit2.Response
import retrofit2.http.GET

interface ServicioWeb {
    @GET("/")
    suspend fun obtenerDatos(): Response<List<Item.Item0>>
}

