package com.example.compose.pokemonapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.pokemonapp.domain.model.PokemonModel
import com.example.compose.pokemonapp.domain.repository.Repository
import com.example.compose.pokemonapp.domain.repository.RepositoryImpl
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

sealed interface PokemonUiState {
    data class Success(val pokemonList: List<PokemonModel>) : PokemonUiState
    data object Error : PokemonUiState
    data object Loading : PokemonUiState
}

class PokemonViewModel(private val repository: RepositoryImpl): ViewModel() {
    var pokemonUiState: PokemonUiState by mutableStateOf(PokemonUiState.Loading)
        private set

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        viewModelScope.launch {
            pokemonUiState = try {
                val resultList = repository.getPokemonList()
                PokemonUiState.Success(resultList)
            } catch (e: Exception) {
                PokemonUiState.Error
            }
        }
    }
}