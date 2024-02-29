package br.com.fiap.tempolafora.models

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("temp_c") val temperature: Double,
    @SerializedName("feelslike_c") val feelsTemperature: Double,
    @SerializedName("is_day") val isDay: Int,
    val humidity : Int,
    val condition: Condition
)
