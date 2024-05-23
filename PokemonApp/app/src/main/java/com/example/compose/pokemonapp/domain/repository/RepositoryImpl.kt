package com.example.compose.pokemonapp.domain.repository

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.example.compose.pokemonapp.data.PokemonService
import com.example.compose.pokemonapp.data.model.AreaEncounters
import com.example.compose.pokemonapp.data.model.LocationArea
import com.example.compose.pokemonapp.data.model.Pokemon
import com.example.compose.pokemonapp.domain.model.PokemonModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val restTemplate: PokemonService): Repository {
    private var pokemonList = emptyList<PokemonModel>()

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

    internal fun processLocation(location: String): String {
        return location.replace("-", " ").replaceFirstChar { it.uppercase() }
    }

    internal fun createLocationsListWithSizeBounded(locations: List<String>): List<String> {
        return locations.take(3).map { processLocation(it) }
    }

    override suspend fun getPokemonList(): List<PokemonModel> {
        if (pokemonList.isNotEmpty()) return pokemonList

        val initialList = getInitialList()

        // put pokemon names into a String list
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