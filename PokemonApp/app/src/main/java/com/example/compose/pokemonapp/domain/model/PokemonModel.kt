package com.example.compose.pokemonapp.domain.model

import com.example.compose.pokemonapp.data.model.Sprites
import java.io.Serializable

data class PokemonModel(
    val id: Int,
    val baseExperience: Int,
    val height: Int,
    val isDefault: Boolean,
    val locations: List<String>,
    var name: String,
    val order: Int,
    val sprites: Sprites,
    val weight: Int
): Serializable