package br.com.fiap.tempolafora.service

import br.com.fiap.tempolafora.models.Info
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current.json")
    fun getWeather(@Query("q") q: String, @Query("key") key: String = "e0b6844f172a446bab5210501242702", @Query("lang") lang : String = "pt"): Call<Info>
}