package com.example.marsphotos.data

import com.example.marsphotos.network.CalificacionFinal
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object LocatorFinal {
    private const val Base_Url = "https://sicenet.surguanajuato.tecnm.mx"




    val retrofitFinal = Retrofit.Builder()
        .baseUrl(Base_Url)
        .client(ServiceLocator.client)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    val serviceFinal:CalificacionFinal= retrofitFinal.create(CalificacionFinal::class.java)

}