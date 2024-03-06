package com.example.marsphotos.data

import com.example.marsphotos.network.CalificacionUnica
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object LocatorCalificacion {
    private const val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx"





    val retrofitCAL = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(ServiceLocator.client)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    val serviceCAL:CalificacionUnica= retrofitCAL.create(CalificacionUnica::class.java)
}