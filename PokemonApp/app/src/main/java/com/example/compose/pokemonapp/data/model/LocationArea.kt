package com.example.compose.pokemonapp.data.model

import com.google.gson.annotations.SerializedName

data class LocationArea(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)