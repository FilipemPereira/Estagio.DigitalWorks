package com.example.compose.pokemonapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("is_default")
    val isDefault: Boolean?,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("sprites")
    val sprites: Sprites?,
    @SerializedName("weight")
    val weight: Int?
): Serializable