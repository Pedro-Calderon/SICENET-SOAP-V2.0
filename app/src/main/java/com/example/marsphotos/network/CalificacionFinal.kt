package com.example.marsphotos.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CalificacionFinal {
    @Headers(
        "Content-Type: text/xml",
        "Accept-Charset: utf-8",
        "SOAPAction: \"http://tempuri.org/getAllCalifFinalByAlumnos\""
        //"Cookie: ASP.NET_SessionId=p0vpmhuu4r1tqe3zwlzmwv55;"

    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun cargaFinal(@Body requestBody: RequestBody): Response<ResponseBody>
}