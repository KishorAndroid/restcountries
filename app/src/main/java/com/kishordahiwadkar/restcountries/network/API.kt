package com.kishordahiwadkar.restcountries.network

import com.kishordahiwadkar.restcountries.BuildConfig
import com.kishordahiwadkar.restcountries.models.Country
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RestCountriesService {

    @GET("region/{value}")
    fun listCountries(@Path("value") value: String): Call<List<Country>>
}

val clientBuilder: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request()

        val newRequest: Request

        newRequest = request.newBuilder()
            .addHeader("x-rapidapi-key", BuildConfig.apikey)
            .addHeader("x-rapidapi-host", BuildConfig.apihost)
            .build()

        val response = chain.proceed(newRequest)

        response
    }.build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://restcountries-v1.p.rapidapi.com/")
    .client(clientBuilder)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val service: RestCountriesService = retrofit.create(RestCountriesService::class.java)