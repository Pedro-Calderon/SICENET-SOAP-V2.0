package com.example.marsphotos.data

import com.example.marsphotos.network.CalificacionUnica
import com.example.marsphotos.network.CargaAcademica
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object LocatorCargaAcademica {
    private const val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx"





    val retrofitCarga = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(ServiceLocator.client)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    val serviceCarga: CargaAcademica = retrofitCarga.create(CargaAcademica::class.java)
}