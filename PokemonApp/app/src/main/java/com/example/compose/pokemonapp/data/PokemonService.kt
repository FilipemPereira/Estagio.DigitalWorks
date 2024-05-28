package com.example.compose.pokemonapp.data

import com.example.compose.pokemonapp.data.model.AreaEncounters
import com.example.compose.pokemonapp.data.model.Pokemon
import com.example.compose.pokemonapp.data.model.PokemonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * A interface that represents the request that can be made to the api
 */
interface PokemonService {
    /**
     * getPokemonList retrieves the PokemonList object from the "pokemon" endpoint and returns a response
     */
    @GET("pokemon")
    suspend fun getPokemonList(): Response<PokemonList>

    /**
     * getPokemon retrieves the Pokemon object from the "pokemon/{id}" endpoint and returns a response
     */
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") name: String): Response<Pokemon>

    /**
     * getLocationAreaEncounters retrieves the AreaEncounters object from the "pokemon/{id}/encounters}"
     * endpoint and returns a response
     */
    @GET("pokemon/{id}/encounters")
    suspend fun getLocationAreaEncounters(@Path("id") id: Int): Response<AreaEncounters>
}