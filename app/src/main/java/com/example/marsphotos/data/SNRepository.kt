package com.example.marsphotos.data

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface AccesoLoginService {
    @Headers(
        "Content-Type: text/xml",
        "Accept-Charset: utf-8",
        "SOAPAction: http://tempuri.org/accesoLogin"
        //"Cookie: .ASPXANONYMOUS=oYH-UhWa2gEkAAAANDczOWQzNDktMjE5My00Mzg1LThlMzgtY2Y2Yjg1MTljZmRitRVlAn19XfqGCMcBgjm8mI42GB41;"

    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun realizarAccesoLogin(@Body requestBody: RequestBody): Response<ResponseBody>
}

