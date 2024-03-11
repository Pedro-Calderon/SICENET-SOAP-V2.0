package com.example.marsphotos.data

import com.example.marsphotos.network.CalificacionUnica
import com.example.marsphotos.network.Kardex
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object LocatorKardex {
    private const val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx"


    val retrofitKardex = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(ServiceLocator.client)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    val serviceKardex: Kardex = retrofitKardex.create(Kardex::class.java)
}