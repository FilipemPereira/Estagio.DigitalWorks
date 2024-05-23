package com.example.compose.pokemonapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.compose.pokemonapp.domain.repository.RepositoryImpl
import kotlinx.coroutines.flow.flow

class PokemonViewModel(private val repository: RepositoryImpl): ViewModel() {
    fun getPokemonList() = flow {
        emit(repository.getPokemonList())
    }
}