import android.annotation.SuppressLint
import android.util.Log
import com.example.marsphotos.data.ServiceLocator
import com.example.marsphotos.network.PerfilSice

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object LocatorAlumnos {
    private const val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx"

    private val client = ServiceLocator.client

    val retrofitAL = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    val serviceAL: PerfilSice = retrofitAL.create(PerfilSice::class.java)

    var cok=""

}
