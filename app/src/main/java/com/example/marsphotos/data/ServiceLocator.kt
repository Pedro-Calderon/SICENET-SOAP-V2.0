package com.example.marsphotos.data


import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.example.marsphotos.data.AddCookiesInterceptor.Companion.PREF_COOKIES
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@SuppressLint("StaticFieldLeak")
object ServiceLocator {
    private const val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx"

    lateinit var context: Context // Contexto que se asignará en algún momento

    /*
    private fun getCookies(): HashSet<String> {

        return PreferenceManager.getDefaultSharedPreferences(context)
            .getStringSet(PREF_COOKIES, HashSet()) as HashSet<String>
    }

    private fun saveCookies(cookies: HashSet<String>) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putStringSet(PREF_COOKIES, cookies)
            .apply()
    }*/
    fun getCookies(): HashSet<String> {
        val cookies = PreferenceManager.getDefaultSharedPreferences(context)
            .getStringSet(PREF_COOKIES, HashSet()) as HashSet<String>
      //  Log.d("Cookies get", "Retrieved cookies: $cookies")
        return cookies
    }

    private fun saveCookies(cookies: HashSet<String>) {
       // Log.d("Cookies save", "Saving cookies: $cookies")
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putStringSet(PREF_COOKIES, cookies)
            .apply()
    }
    fun clearCookies(context: Context) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().remove(PREF_COOKIES).apply()
    }

     val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            val cookies = getCookies()
            for (cookie in cookies) {
                builder.addHeader("Cookie", cookie)
            }
            chain.proceed(builder.build())
        }
        .addInterceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                val cookies = getCookies()
                for (header in originalResponse.headers("Set-Cookie")) {
                    cookies.add(header)
                }
                saveCookies(cookies)
            }
            originalResponse
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: AccesoLoginService = retrofit.create(AccesoLoginService::class.java)
}
