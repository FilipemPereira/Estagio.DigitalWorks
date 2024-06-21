package com.example.compose.pokemonapp.domain.repository

import android.util.Log
import com.example.compose.pokemonapp.data.PokemonService
import com.example.compose.pokemonapp.data.model.Pokemon
import com.example.compose.pokemonapp.domain.model.PokemonModel
import javax.inject.Inject

/**
 * Implementation of the repository that fetch and create a pokemon list from the api
 */
class RepositoryImpl @Inject constructor(private val restTemplate: PokemonService): Repository {
    // Contains the actual pokemon list
    private var pokemonList = emptyList<PokemonModel>()

    /**
     * getInitialList fetch the initial objet used for create the pokemon list from the api
     */
    internal suspend fun getInitialList(): List<Pokemon> {
        return try {
            val pokemonResponse = restTemplate.getPokemonList()
            if (pokemonResponse.isSuccessful) {
                val initialList = pokemonResponse.body()?.results
                initialList ?: emptyList()

            } else emptyList()
        } catch (exception: Exception) {
            Log.d("MyTag", exception.message.toString())
            emptyList()
        }
    }

    /**
     * processLocation get a string as argument and returns a new string without the '-' char and
     * with the first char in uppercase
     * @param location - old string
     * @return the old string transformed
     */
    internal fun processLocation(location: String): String {
        return location.replace("-", " ").replaceFirstChar { it.uppercase() }
    }

    /**
     * createLocationsListWithSizeBounded gets a list of locations (strings) and returns the first 3 elements,
     * if possible, where each location where transformed by the processLocation method
     * @param locations - list of locations that comes from the api
     * @return a list of 3, 2 or 1 element
     */
    internal fun createLocationsListWithSizeBounded(locations: List<String>): List<String> {
        return locations.take(3).map { processLocation(it) }
    }

    /**
     * getPokemonList returns the list of pokemons created with the the initial object retrieved from
     * the api after processing it
     */
    override suspend fun getPokemonList(): List<PokemonModel> {
        if (pokemonList.isNotEmpty()) return pokemonList

        val initialList = getInitialList()

        // put pokemon names into a list of strings
        val names = initialList.map { it.name }.filterNotNull()

        // create the list of models that will be passed to the viewModel
        pokemonList = names
            .map { restTemplate.getPokemon(it) }
            .filter { it.isSuccessful }
            .map { it.body() }
            .filterNotNull()
            .map {pokemonApi ->
                // constructs the pokemonModel that will be shown in the app
                val locations = restTemplate.getLocationAreaEncounters(pokemonApi.id ?: 0)
                val newName = pokemonApi.name?.replaceFirstChar { it.uppercase() } ?: ""
                val locationsList = if (locations.isSuccessful && locations.body() != null)
                    createLocationsListWithSizeBounded(locations.body()!!.map { it.locationArea.name })  else emptyList()
                val model = PokemonModel(
                    pokemonApi.id!!, pokemonApi.baseExperience!!,pokemonApi.height!!, pokemonApi.isDefault!!,
                    locationsList, newName, pokemonApi.order!!, pokemonApi.sprites!!, pokemonApi.weight!!
                )
                model
            }

        return pokemonList
    }
}