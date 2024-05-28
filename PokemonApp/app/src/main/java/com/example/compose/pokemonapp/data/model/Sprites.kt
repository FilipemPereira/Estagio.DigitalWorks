package com.example.compose.pokemonapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Class that represents the the object where the url of the pokemon images are
 */
data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?,
): Serializable