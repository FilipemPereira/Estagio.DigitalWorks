package com.example.compose.pokemonapp.data

import com.example.compose.pokemonapp.data.model.AreaEncounters
import com.example.compose.pokemonapp.data.model.Pokemon
import com.example.compose.pokemonapp.data.model.PokemonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemonList(): Response<PokemonList>

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") name: String): Response<Pokemon>

    @GET("pokemon/{id}/encounters")
    suspend fun getLocationAreaEncounters(@Path("id") id: Int): Response<AreaEncounters>
}