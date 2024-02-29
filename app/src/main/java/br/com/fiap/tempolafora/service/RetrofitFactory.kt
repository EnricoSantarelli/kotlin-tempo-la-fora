package br.com.fiap.tempolafora.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val url = "http://api.weatherapi.com/v1/"

    private val retrofitFactory = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()

    fun getWeatherService(): WeatherService {
        return retrofitFactory.create(WeatherService::class.java)
    }
}