package com.example.compose.pokemonapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.pokemonapp.domain.repository.RepositoryImpl
import javax.inject.Inject

/**
 * Factory for the ViewModel that takes a repository as a dependency
 */
class PokemonViewModelFactory @Inject constructor(private val repositoryImpl: RepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonViewModel(repositoryImpl) as T
    }
}