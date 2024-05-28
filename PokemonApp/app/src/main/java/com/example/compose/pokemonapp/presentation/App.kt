package com.example.compose.pokemonapp.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Makes the android app works properly with Hilt Library
 */
@HiltAndroidApp
class App: Application()