package com.example.compose.pokemonapp.presentation.di

import com.example.compose.pokemonapp.BuildConfig
import com.example.compose.pokemonapp.data.PokemonService
import com.example.compose.pokemonapp.domain.repository.Repository
import com.example.compose.pokemonapp.domain.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
/**
 * Object that provides all the dependencies needed for the application
 */
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonService(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }


    @Provides
    @Singleton
    fun provideRepository(restService: PokemonService): Repository {
        return RepositoryImpl(restService)
    }
}