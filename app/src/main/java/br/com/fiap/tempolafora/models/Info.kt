package br.com.fiap.tempolafora.models

import com.google.gson.annotations.SerializedName

data class Info(
    val location: Location,
    @SerializedName("current") val weather: Weather
)
