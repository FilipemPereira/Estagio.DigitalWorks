package com.example.compose.pokemonapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Class that represents the element that are stored in the array of area Encounters
 */
data class AreaEncountersItem(
    @SerializedName("location_area")
    val locationArea: LocationArea
)