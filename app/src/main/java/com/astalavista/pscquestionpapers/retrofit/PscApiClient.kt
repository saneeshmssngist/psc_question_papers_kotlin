package com.astalavista.psc_kerala.Retrofit

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PscApiClient {

    val MASTEERURL = "https://raw.githubusercontent.com/saneeshmssngist/psc_questionpapers/master/"
//    val MASTEERURL = "https://saneeshmsquizrr.000webhostapp.com/"

    var retrofit: Retrofit? = null

    /* Recreating response before returning because response body can be read only once */ val aPiClient: Retrofit?
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(15, TimeUnit.SECONDS)
            httpClient.readTimeout(15, TimeUnit.SECONDS)
            httpClient.writeTimeout(15, TimeUnit.SECONDS)

            httpClient.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .build()

                val response = chain.proceed(request)
                val rawJson = response.body()!!.string()
                response.newBuilder()
                    .body(okhttp3.ResponseBody.create(response.body()!!.contentType(), rawJson))
                    .build()
            }
            httpClient.addInterceptor(interceptor)
            httpClient.hostnameVerifier { hostname, session -> true }

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(MASTEERURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }

            return retrofit
        }
}


