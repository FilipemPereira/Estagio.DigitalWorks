package com.example.compose.pokemonapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Class that represents the initial object that have to be fetched from the api
 */
data class PokemonList(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<Pokemon>?
)