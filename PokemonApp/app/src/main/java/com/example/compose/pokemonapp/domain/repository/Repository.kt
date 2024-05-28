package com.example.compose.pokemonapp.domain.repository

import com.example.compose.pokemonapp.domain.model.PokemonModel

/**
 * Repository that fetch and create a pokemon list from the api
 */
interface Repository {
    suspend fun getPokemonList(): List<PokemonModel>
}