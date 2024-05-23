package com.example.compose.pokemonapp.data.model

import com.google.gson.annotations.SerializedName

data class AreaEncountersItem(
    @SerializedName("location_area")
    val locationArea: LocationArea
)