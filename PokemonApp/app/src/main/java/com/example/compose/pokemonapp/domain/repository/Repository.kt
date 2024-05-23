package com.example.compose.pokemonapp.domain.repository

import com.example.compose.pokemonapp.domain.model.PokemonModel

interface Repository {
    suspend fun getPokemonList(): List<PokemonModel>
}