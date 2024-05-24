package com.example.compose.pokemonapp.repository.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.compose.pokemonapp.data.PokemonService
import com.example.compose.pokemonapp.data.model.AreaEncounters
import com.example.compose.pokemonapp.data.model.AreaEncountersItem
import com.example.compose.pokemonapp.data.model.LocationArea
import com.example.compose.pokemonapp.data.model.Pokemon
import com.example.compose.pokemonapp.data.model.PokemonList
import com.example.compose.pokemonapp.data.model.Sprites
import com.example.compose.pokemonapp.domain.model.PokemonModel
import com.example.compose.pokemonapp.domain.repository.RepositoryImpl
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class RepositoryTest {
    private val restService: PokemonService = mockk<PokemonService>(relaxed = true)
    private val repository: RepositoryImpl = RepositoryImpl(restService)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getPokemonListTest_emptyListResponse_returnCorrectList() = runBlocking {
        //mock ...
        val result = repository.getPokemonList()

        Truth.assertThat(result).isEqualTo(emptyList<Pokemon>())
    }

    @Test
    fun getPokemonObject_successfulResponse_returnCorrectList() = runBlocking {
        val expectedInitialList: List<Pokemon> = listOf(
            Pokemon(null,null,null,null,null,"beedrill",null, Sprites(null),null)
        )

        val expectedPokemonList: List<Pokemon> = listOf(
            Pokemon(15, 178, 10, true, "https://pokeapi.co/api/v2/pokemon/15/encounters", "beedrill", 19, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/15.png"), 295),
        )

        val expectedAreEncounters = AreaEncounters().apply {
            add(AreaEncountersItem(LocationArea("ilex-forest-area", "https://pokeapi.co/api/v2/location-area/204/")))
            add(AreaEncountersItem(LocationArea("johto-route-34-area", "https://pokeapi.co/api/v2/location-area/205/")))
            add(AreaEncountersItem(LocationArea("johto-route-35-area", "https://pokeapi.co/api/v2/location-area/206/")))
            add(AreaEncountersItem(LocationArea("johto-route-36-area", "https://pokeapi.co/api/v2/location-area/209/")))
            add(AreaEncountersItem(LocationArea("johto-route-37-area", "https://pokeapi.co/api/v2/location-area/210/")))
            add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
            add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
            add(AreaEncountersItem(LocationArea("lake-of-rage-area", "https://pokeapi.co/api/v2/location-area/242/")))
            add(AreaEncountersItem(LocationArea("kanto-route-26-area", "https://pokeapi.co/api/v2/location-area/287/")))
            add(AreaEncountersItem(LocationArea("kanto-route-27-area", "https://pokeapi.co/api/v2/location-area/288/")))
            add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
            add(AreaEncountersItem(LocationArea("kanto-route-2-north-towards-pewter-city", "https://pokeapi.co/api/v2/location-area/320/")))
            add(AreaEncountersItem(LocationArea("viridian-forest-area", "https://pokeapi.co/api/v2/location-area/321/")))
            add(AreaEncountersItem(LocationArea("unova-route-12-area", "https://pokeapi.co/api/v2/location-area/646/")))
            add(AreaEncountersItem(LocationArea("azalea-town-area", "https://pokeapi.co/api/v2/location-area/798/")))
            add(AreaEncountersItem(LocationArea("alola-route-4-area", "https://pokeapi.co/api/v2/location-area/1046/")))
        }

        val expectedLocations = expectedAreEncounters.map { it.locationArea.name }

        val expectedPokemon = PokemonModel(15, 178, 10, true, listOf("Ilex forest area", "Johto route 34 area", "Johto route 35 area"), "Beedrill", 19,
            Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/15.png"), 295)

        coEvery { restService.getPokemonList() } returns Response.success(PokemonList(null, null, null, expectedInitialList))
        val names = expectedInitialList.map { it.name }.filterNotNull()
        expectedPokemonList.forEach {pokemon ->
            names.map { coEvery { restService.getPokemon(it) } returns Response.success(pokemon) }
        }
        coEvery { restService.getLocationAreaEncounters(15) } returns Response.success(expectedAreEncounters)

        val pokemon = expectedPokemonList.first()
        val newName = pokemon.name!!.replaceFirstChar { it.uppercase() }
        val model = PokemonModel(15,pokemon.baseExperience!!, pokemon.height!!, pokemon.isDefault!!, repository.createLocationsListWithSizeBounded(expectedLocations), newName, pokemon.order!!, pokemon.sprites!!, pokemon.weight!!)

        val result = repository.getPokemonList()

        assertEquals(model, expectedPokemon)
        Truth.assertThat(result.first()).isEqualTo(model)
    }


    @Test
    fun getPokemonListTest_successfulResponse_returnCorrectList() = runBlocking {
        val expectedInitialList: List<Pokemon> = listOf(
            Pokemon(null,null,null,null,null,"Bulbasaur",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Ivysaur",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Venusaur",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Charmander",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Charmeleon",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Charizard",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Squirtle",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Wartortle",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Blastoise",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Caterpie",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Metapod",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Butterfree",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Weedle",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Kakuna",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Beedrill",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Pidgey",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Pidgeotto",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Pidgeot",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Rattata",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Raticate",null, Sprites(null),null)
        )

        val expectedPokemonList: List<Pokemon> = listOf(
            Pokemon(1, 64, 7, true,"https://pokeapi.co/api/v2/pokemon/1/encounters", "bulbasaur", 1, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"), 69),
            Pokemon(2, 142, 10, true, "https://pokeapi.co/api/v2/pokemon/2/encounters", "ivysaur", 2, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"), 130),
            Pokemon(3, 263, 20, true, "https://pokeapi.co/api/v2/pokemon/3/encounters", "venusaur", 3, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png"), 1000),
            Pokemon(4, 62, 6, true, "https://pokeapi.co/api/v2/pokemon/4/encounters", "charmander", 5, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"), 85),
            Pokemon(5, 142, 11, true, "https://pokeapi.co/api/v2/pokemon/5/encounters", "charmeleon", 6, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png"), 190),
            Pokemon(6, 267, 17, true, "https://pokeapi.co/api/v2/pokemon/6/encounters", "charizard", 7, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png"), 905),
            Pokemon(7, 63, 5, true, "https://pokeapi.co/api/v2/pokemon/7/encounters", "squirtle", 10, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png"), 90),
            Pokemon(8,  142, 10, true, "https://pokeapi.co/api/v2/pokemon/8/encounters", "wartortle", 11, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/8.png"), 225),
            Pokemon(9,  265, 16, true, "https://pokeapi.co/api/v2/pokemon/9/encounters", "blastoise", 12, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png"), 855),
            Pokemon(10, 39, 3, true, "https://pokeapi.co/api/v2/pokemon/10/encounters", "caterpie", 14, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png"), 29),
            Pokemon(11, 72, 7, true, "https://pokeapi.co/api/v2/pokemon/11/encounters", "metapod", 15, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/11.png"), 99),
            Pokemon(12, 198, 11, true, "https://pokeapi.co/api/v2/pokemon/12/encounters", "butterfree", 16, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/12.png"), 320),
            Pokemon(13, 39, 3, true, "https://pokeapi.co/api/v2/pokemon/13/encounters", "weedle", 17, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/13.png"), 32),
            Pokemon(14, 72, 6, true, "https://pokeapi.co/api/v2/pokemon/14/encounters", "kakuna", 18, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/14.png"), 100),
            Pokemon(15, 178, 10, true, "https://pokeapi.co/api/v2/pokemon/15/encounters", "beedrill", 19, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/15.png"), 295),
            Pokemon(16, 50, 3, true, "https://pokeapi.co/api/v2/pokemon/16/encounters", "pidgey", 21, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/16.png"), 18),
            Pokemon(17, 122, 11, true, "https://pokeapi.co/api/v2/pokemon/17/encounters", "pidgeotto", 22, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/17.png"), 300),
            Pokemon(18, 216, 15, true, "https://pokeapi.co/api/v2/pokemon/18/encounters", "pidgeot", 23, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/18.png"), 395),
            Pokemon(19, 51, 3, true, "https://pokeapi.co/api/v2/pokemon/19/encounters", "rattata", 25, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/19.png"), 35),
            Pokemon(20, 145, 7, true, "https://pokeapi.co/api/v2/pokemon/20/encounters", "raticate", 27, Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/20.png"), 185)

        )

        val expectedAreaEncounters = listOf<AreaEncounters>(
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("cerulean-city-area", "https://pokeapi.co/api/v2/location-area/281/")))
                add(AreaEncountersItem(LocationArea("pallet-town-area", "https://pokeapi.co/api/v2/location-area/285/")))
                add(AreaEncountersItem(LocationArea("lumiose-city-area", "https://pokeapi.co/api/v2/location-area/779/")))
                add(AreaEncountersItem(LocationArea("alola-route-2-main", "https://pokeapi.co/api/v2/location-area/1040/")))
            },
            AreaEncounters(),
            AreaEncounters(),
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("pallet-town-area", "https://pokeapi.co/api/v2/location-area/285/")))
                add(AreaEncountersItem(LocationArea("kanto-route-24-area", "https://pokeapi.co/api/v2/location-area/314/")))
                add(AreaEncountersItem(LocationArea("lumiose-city-area", "https://pokeapi.co/api/v2/location-area/779/")))
                add(AreaEncountersItem(LocationArea("alola-route-3-main", "https://pokeapi.co/api/v2/location-area/1043/")))
            },
            AreaEncounters(),
            AreaEncounters(),
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("vermilion-city-area", "https://pokeapi.co/api/v2/location-area/282/")))
                add(AreaEncountersItem(LocationArea("pallet-town-area", "https://pokeapi.co/api/v2/location-area/285/")))
                add(AreaEncountersItem(LocationArea("lumiose-city-area", "https://pokeapi.co/api/v2/location-area/779/")))
                add(AreaEncountersItem(LocationArea("seaward-cave-area", "https://pokeapi.co/api/v2/location-area/1132/")))
            },
            AreaEncounters(),
            AreaEncounters(),
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("eterna-forest-area", "https://pokeapi.co/api/v2/location-area/9/")))
                add(AreaEncountersItem(LocationArea("sinnoh-route-204-south-towards-jubilife-city", "https://pokeapi.co/api/v2/location-area/144/")))
                add(AreaEncountersItem(LocationArea("sinnoh-route-204-north-towards-floaroma-town", "https://pokeapi.co/api/v2/location-area/145/")))
                add(AreaEncountersItem(LocationArea("johto-route-30-area", "https://pokeapi.co/api/v2/location-area/187/")))
                add(AreaEncountersItem(LocationArea("johto-route-31-area", "https://pokeapi.co/api/v2/location-area/188/")))
                add(AreaEncountersItem(LocationArea("ilex-forest-area", "https://pokeapi.co/api/v2/location-area/204/")))
                add(AreaEncountersItem(LocationArea("johto-route-34-area", "https://pokeapi.co/api/v2/location-area/205/")))
                add(AreaEncountersItem(LocationArea("johto-route-35-area", "https://pokeapi.co/api/v2/location-area/206/")))
                add(AreaEncountersItem(LocationArea("national-park-area", "https://pokeapi.co/api/v2/location-area/207/")))
                add(AreaEncountersItem(LocationArea("unknown-all-bugs-area", "https://pokeapi.co/api/v2/location-area/208/")))
                add(AreaEncountersItem(LocationArea("johto-route-36-area", "https://pokeapi.co/api/v2/location-area/209/")))
                add(AreaEncountersItem(LocationArea("johto-route-37-area", "https://pokeapi.co/api/v2/location-area/210/")))
                add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
                add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
                add(AreaEncountersItem(LocationArea("lake-of-rage-area", "https://pokeapi.co/api/v2/location-area/242/")))
                add(AreaEncountersItem(LocationArea("kanto-route-26-area", "https://pokeapi.co/api/v2/location-area/287/")))
                add(AreaEncountersItem(LocationArea("kanto-route-27-area", "https://pokeapi.co/api/v2/location-area/288/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
                add(AreaEncountersItem(LocationArea("kanto-route-24-area", "https://pokeapi.co/api/v2/location-area/314/")))
                add(AreaEncountersItem(LocationArea("kanto-route-25-area", "https://pokeapi.co/api/v2/location-area/315/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-north-towards-pewter-city", "https://pokeapi.co/api/v2/location-area/320/")))
                add(AreaEncountersItem(LocationArea("viridian-forest-area", "https://pokeapi.co/api/v2/location-area/321/")))
                add(AreaEncountersItem(LocationArea("pattern-bush-area", "https://pokeapi.co/api/v2/location-area/500/")))
                add(AreaEncountersItem(LocationArea("kalos-route-2-area", "https://pokeapi.co/api/v2/location-area/713/")))
                add(AreaEncountersItem(LocationArea("santalune-forest-area", "https://pokeapi.co/api/v2/location-area/734/")))
                add(AreaEncountersItem(LocationArea("azalea-town-area", "https://pokeapi.co/api/v2/location-area/798/")))
                add(AreaEncountersItem(LocationArea("alola-route-1-east", "https://pokeapi.co/api/v2/location-area/1035/")))
                add(AreaEncountersItem(LocationArea("alola-route-1-south", "https://pokeapi.co/api/v2/location-area/1037/")))
                add(AreaEncountersItem(LocationArea("alola-route-1-west", "https://pokeapi.co/api/v2/location-area/1039/")))
                add(AreaEncountersItem(LocationArea("alola-route-5-area", "https://pokeapi.co/api/v2/location-area/1047/")))
                add(AreaEncountersItem(LocationArea("lush-jungle-north", "https://pokeapi.co/api/v2/location-area/1095/")))
                add(AreaEncountersItem(LocationArea("lush-jungle-south", "https://pokeapi.co/api/v2/location-area/1096/")))
                add(AreaEncountersItem(LocationArea("lush-jungle-west", "https://pokeapi.co/api/v2/location-area/1097/")))
                add(AreaEncountersItem(LocationArea("melemelo-meadow-area", "https://pokeapi.co/api/v2/location-area/1102/")))
            },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("eterna-forest-area", "https://pokeapi.co/api/v2/location-area/9/")))
                add(AreaEncountersItem(LocationArea("johto-route-30-area", "https://pokeapi.co/api/v2/location-area/187/")))
                add(AreaEncountersItem(LocationArea("johto-route-31-area", "https://pokeapi.co/api/v2/location-area/188/")))
                add(AreaEncountersItem(LocationArea("ilex-forest-area", "https://pokeapi.co/api/v2/location-area/204/")))
                add(AreaEncountersItem(LocationArea("johto-route-34-area", "https://pokeapi.co/api/v2/location-area/205/")))
                add(AreaEncountersItem(LocationArea("johto-route-35-area", "https://pokeapi.co/api/v2/location-area/206/")))
                add(AreaEncountersItem(LocationArea("national-park-area", "https://pokeapi.co/api/v2/location-area/207/")))
                add(AreaEncountersItem(LocationArea("johto-route-36-area", "https://pokeapi.co/api/v2/location-area/209/")))
                add(AreaEncountersItem(LocationArea("johto-route-37-area", "https://pokeapi.co/api/v2/location-area/210/")))
                add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
                add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
                add(AreaEncountersItem(LocationArea("lake-of-rage-area", "https://pokeapi.co/api/v2/location-area/242/")))
                add(AreaEncountersItem(LocationArea("kanto-route-26-area", "https://pokeapi.co/api/v2/location-area/287/")))
                add(AreaEncountersItem(LocationArea("kanto-route-27-area", "https://pokeapi.co/api/v2/location-area/288/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
                add(AreaEncountersItem(LocationArea("kanto-route-24-area", "https://pokeapi.co/api/v2/location-area/314/")))
                add(AreaEncountersItem(LocationArea("kanto-route-25-area", "https://pokeapi.co/api/v2/location-area/315/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-north-towards-pewter-city", "https://pokeapi.co/api/v2/location-area/320/")))
                add(AreaEncountersItem(LocationArea("viridian-forest-area", "https://pokeapi.co/api/v2/location-area/321/")))
                add(AreaEncountersItem(LocationArea("pattern-bush-area", "https://pokeapi.co/api/v2/location-area/500/")))
                add(AreaEncountersItem(LocationArea("unova-route-12-area", "https://pokeapi.co/api/v2/location-area/646/")))
                add(AreaEncountersItem(LocationArea("santalune-forest-area", "https://pokeapi.co/api/v2/location-area/734/")))
                add(AreaEncountersItem(LocationArea("azalea-town-area", "https://pokeapi.co/api/v2/location-area/798/")))
                add(AreaEncountersItem(LocationArea("alola-route-1-south", "https://pokeapi.co/api/v2/location-area/1037/")))
                add(AreaEncountersItem(LocationArea("alola-route-1-west", "https://pokeapi.co/api/v2/location-area/1039/")))
                add(AreaEncountersItem(LocationArea("alola-route-5-area", "https://pokeapi.co/api/v2/location-area/1047/")))
                add(AreaEncountersItem(LocationArea("lush-jungle-north", "https://pokeapi.co/api/v2/location-area/1095/")))
                add(AreaEncountersItem(LocationArea("lush-jungle-south", "https://pokeapi.co/api/v2/location-area/1096/")))
                add(AreaEncountersItem(LocationArea("lush-jungle-west", "https://pokeapi.co/api/v2/location-area/1097/")))
                add(AreaEncountersItem(LocationArea("melemele-meadow-area", "https://pokeapi.co/api/v2/location-area/1102/")))
            },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("ilex-forest-area", "https://pokeapi.co/api/v2/location-area/204/")))
                add(AreaEncountersItem(LocationArea("johto-route-34-area", "https://pokeapi.co/api/v2/location-area/205/")))
                add(AreaEncountersItem(LocationArea("johto-route-35-area", "https://pokeapi.co/api/v2/location-area/206/")))
                add(AreaEncountersItem(LocationArea("johto-route-36-area", "https://pokeapi.co/api/v2/location-area/209/")))
                add(AreaEncountersItem(LocationArea("johto-route-37-area", "https://pokeapi.co/api/v2/location-area/210/")))
                add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
                add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
                add(AreaEncountersItem(LocationArea("lake-of-rage-area", "https://pokeapi.co/api/v2/location-area/242/")))
                add(AreaEncountersItem(LocationArea("kanto-route-26-area", "https://pokeapi.co/api/v2/location-area/287/")))
                add(AreaEncountersItem(LocationArea("kanto-route-27-area", "https://pokeapi.co/api/v2/location-area/288/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
                add(AreaEncountersItem(LocationArea("kanto-route-24-area", "https://pokeapi.co/api/v2/location-area/314/")))
                add(AreaEncountersItem(LocationArea("kanto-route-25-area", "https://pokeapi.co/api/v2/location-area/315/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-north-towards-pewter-city", "https://pokeapi.co/api/v2/location-area/320/")))
                add(AreaEncountersItem(LocationArea("viridian-forest-area", "https://pokeapi.co/api/v2/location-area/321/")))
                add(AreaEncountersItem(LocationArea("unova-route-12-area", "https://pokeapi.co/api/v2/location-area/646/")))
                add(AreaEncountersItem(LocationArea("azalea-town-area", "https://pokeapi.co/api/v2/location-area/798/")))
                add(AreaEncountersItem(LocationArea("alola-route-5-area", "https://pokeapi.co/api/v2/location-area/1047/")))
                add(AreaEncountersItem(LocationArea("lush-jungle-north", "https://pokeapi.co/api/v2/location-area/1095/")))
                add(AreaEncountersItem(LocationArea("lush-jungle-south", "https://pokeapi.co/api/v2/location-area/1096/")))
                add(AreaEncountersItem(LocationArea("lush-jungle-west", "https://pokeapi.co/api/v2/location-area/1097/")))
                add(AreaEncountersItem(LocationArea("melemele-meadow-area", "https://pokeapi.co/api/v2/location-area/1102/")))
            },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("eterna-forest-area", "https://pokeapi.co/api/v2/location-area/9/")))
                add(AreaEncountersItem(LocationArea("sinnoh-route-204-south-towards-jubilife-city", "https://pokeapi.co/api/v2/location-area/144/")))
                add(AreaEncountersItem(LocationArea("sinnoh-route-204-north-towards-floaroma-town", "https://pokeapi.co/api/v2/location-area/145/")))
                add(AreaEncountersItem(LocationArea("johto-route-30-area", "https://pokeapi.co/api/v2/location-area/187/")))
                add(AreaEncountersItem(LocationArea("johto-route-31-area", "https://pokeapi.co/api/v2/location-area/188/")))
                add(AreaEncountersItem(LocationArea("ilex-forest-area", "https://pokeapi.co/api/v2/location-area/204/")))
                add(AreaEncountersItem(LocationArea("johto-route-34-area", "https://pokeapi.co/api/v2/location-area/205/")))
                add(AreaEncountersItem(LocationArea("johto-route-35-area", "https://pokeapi.co/api/v2/location-area/206/")))
                add(AreaEncountersItem(LocationArea("national-park-area", "https://pokeapi.co/api/v2/location-area/207/")))
                add(AreaEncountersItem(LocationArea("unknown-all-bugs-area", "https://pokeapi.co/api/v2/location-area/208/")))
                add(AreaEncountersItem(LocationArea("johto-route-36-area", "https://pokeapi.co/api/v2/location-area/209/")))
                add(AreaEncountersItem(LocationArea("johto-route-37-area", "https://pokeapi.co/api/v2/location-area/210/")))
                add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
                add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
                add(AreaEncountersItem(LocationArea("lake-of-rage-area", "https://pokeapi.co/api/v2/location-area/242/")))
                add(AreaEncountersItem(LocationArea("kanto-route-26-area", "https://pokeapi.co/api/v2/location-area/287/")))
                add(AreaEncountersItem(LocationArea("kanto-route-27-area", "https://pokeapi.co/api/v2/location-area/288/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
                add(AreaEncountersItem(LocationArea("kanto-route-24-area", "https://pokeapi.co/api/v2/location-area/314/")))
                add(AreaEncountersItem(LocationArea("kanto-route-25-area", "https://pokeapi.co/api/v2/location-area/315/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-north-towards-pewter-city", "https://pokeapi.co/api/v2/location-area/320/")))
                add(AreaEncountersItem(LocationArea("viridian-forest-area", "https://pokeapi.co/api/v2/location-area/321/")))
                add(AreaEncountersItem(LocationArea("pattern-bush-area", "https://pokeapi.co/api/v2/location-area/500/")))
                add(AreaEncountersItem(LocationArea("kalos-route-2-area", "https://pokeapi.co/api/v2/location-area/713/")))
                add(AreaEncountersItem(LocationArea("santalune-forest-area", "https://pokeapi.co/api/v2/location-area/734/")))
                add(AreaEncountersItem(LocationArea("azalea-town-area", "https://pokeapi.co/api/v2/location-area/798/")))
                },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("eterna-forest-area", "https://pokeapi.co/api/v2/location-area/9/")))
                add(AreaEncountersItem(LocationArea("johto-route-30-area", "https://pokeapi.co/api/v2/location-area/187/")))
                add(AreaEncountersItem(LocationArea("johto-route-31-area", "https://pokeapi.co/api/v2/location-area/188/")))
                add(AreaEncountersItem(LocationArea("ilex-forest-area", "https://pokeapi.co/api/v2/location-area/204/")))
                add(AreaEncountersItem(LocationArea("johto-route-34-area", "https://pokeapi.co/api/v2/location-area/205/")))
                add(AreaEncountersItem(LocationArea("johto-route-35-area", "https://pokeapi.co/api/v2/location-area/206/")))
                add(AreaEncountersItem(LocationArea("national-park-area", "https://pokeapi.co/api/v2/location-area/207/")))
                add(AreaEncountersItem(LocationArea("johto-route-36-area", "https://pokeapi.co/api/v2/location-area/209/")))
                add(AreaEncountersItem(LocationArea("johto-route-37-area", "https://pokeapi.co/api/v2/location-area/210/")))
                add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
                add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
                add(AreaEncountersItem(LocationArea("lake-of-rage-area", "https://pokeapi.co/api/v2/location-area/242/")))
                add(AreaEncountersItem(LocationArea("kanto-route-26-area", "https://pokeapi.co/api/v2/location-area/287/")))
                add(AreaEncountersItem(LocationArea("kanto-route-27-area", "https://pokeapi.co/api/v2/location-area/288/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
                add(AreaEncountersItem(LocationArea("kanto-route-24-area", "https://pokeapi.co/api/v2/location-area/314/")))
                add(AreaEncountersItem(LocationArea("kanto-route-25-area", "https://pokeapi.co/api/v2/location-area/315/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-north-towards-pewter-city", "https://pokeapi.co/api/v2/location-area/320/")))
                add(AreaEncountersItem(LocationArea("viridian-forest-area", "https://pokeapi.co/api/v2/location-area/321/")))
                add(AreaEncountersItem(LocationArea("pattern-bush-area", "https://pokeapi.co/api/v2/location-area/500/")))
                add(AreaEncountersItem(LocationArea("unova-route-12-area", "https://pokeapi.co/api/v2/location-area/646/")))
                add(AreaEncountersItem(LocationArea("santalune-forest-area", "https://pokeapi.co/api/v2/location-area/734/")))
                add(AreaEncountersItem(LocationArea("azalea-town-area", "https://pokeapi.co/api/v2/location-area/798/")))
            },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("ilex-forest-area", "https://pokeapi.co/api/v2/location-area/204/")))
                add(AreaEncountersItem(LocationArea("johto-route-34-area", "https://pokeapi.co/api/v2/location-area/205/")))
                add(AreaEncountersItem(LocationArea("johto-route-35-area", "https://pokeapi.co/api/v2/location-area/206/")))
                add(AreaEncountersItem(LocationArea("johto-route-36-area", "https://pokeapi.co/api/v2/location-area/209/")))
                add(AreaEncountersItem(LocationArea("johto-route-37-area", "https://pokeapi.co/api/v2/location-area/210/")))
                add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
                add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
                add(AreaEncountersItem(LocationArea("lake-of-rage-area", "https://pokeapi.co/api/v2/location-area/242/")))
                add(AreaEncountersItem(LocationArea("kanto-route-26-area", "https://pokeapi.co/api/v2/location-area/287/")))
                add(AreaEncountersItem(LocationArea("kanto-route-27-area", "https://pokeapi.co/api/v2/location-area/288/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-north-towards-pewter-city", "https://pokeapi.co/api/v2/location-area/320/")))
                add(AreaEncountersItem(LocationArea("viridian-forest-area", "https://pokeapi.co/api/v2/location-area/321/")))
                add(AreaEncountersItem(LocationArea("unova-route-12-area", "https://pokeapi.co/api/v2/location-area/646/")))
                add(AreaEncountersItem(LocationArea("azalea-town-area", "https://pokeapi.co/api/v2/location-area/798/")))
                add(AreaEncountersItem(LocationArea("alola-route-4-area", "https://pokeapi.co/api/v2/location-area/1046/")))
            },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("sinnoh-route-229-area", "https://pokeapi.co/api/v2/location-area/176/")))
                add(AreaEncountersItem(LocationArea("johto-route-29-area", "https://pokeapi.co/api/v2/location-area/185/")))
                add(AreaEncountersItem(LocationArea("johto-route-30-area", "https://pokeapi.co/api/v2/location-area/187/")))
                add(AreaEncountersItem(LocationArea("johto-route-31-area", "https://pokeapi.co/api/v2/location-area/188/")))
                add(AreaEncountersItem(LocationArea("johto-route-32-area", "https://pokeapi.co/api/v2/location-area/192/")))
                add(AreaEncountersItem(LocationArea("ilex-forest-area", "https://pokeapi.co/api/v2/location-area/204/")))
                add(AreaEncountersItem(LocationArea("johto-route-34-area", "https://pokeapi.co/api/v2/location-area/205/")))
                add(AreaEncountersItem(LocationArea("johto-route-35-area", "https://pokeapi.co/api/v2/location-area/206/")))
                add(AreaEncountersItem(LocationArea("national-park-area", "https://pokeapi.co/api/v2/location-area/207/")))
                add(AreaEncountersItem(LocationArea("johto-route-36-area", "https://pokeapi.co/api/v2/location-area/209/")))
                add(AreaEncountersItem(LocationArea("johto-route-37-area", "https://pokeapi.co/api/v2/location-area/210/")))
                add(AreaEncountersItem(LocationArea("kanto-route-12-area", "https://pokeapi.co/api/v2/location-area/276/")))
                add(AreaEncountersItem(LocationArea("kanto-route-1-area", "https://pokeapi.co/api/v2/location-area/295/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
                add(AreaEncountersItem(LocationArea("kanto-route-3-area", "https://pokeapi.co/api/v2/location-area/297/")))
                add(AreaEncountersItem(LocationArea("kanto-route-5-area", "https://pokeapi.co/api/v2/location-area/299/")))
                add(AreaEncountersItem(LocationArea("kanto-route-6-area", "https://pokeapi.co/api/v2/location-area/300/")))
                add(AreaEncountersItem(LocationArea("kanto-route-7-area", "https://pokeapi.co/api/v2/location-area/301/")))
                add(AreaEncountersItem(LocationArea("kanto-route-8-area", "https://pokeapi.co/api/v2/location-area/302/")))
                add(AreaEncountersItem(LocationArea("kanto-route-11-area", "https://pokeapi.co/api/v2/location-area/305/")))
                add(AreaEncountersItem(LocationArea("kanto-route-13-area", "https://pokeapi.co/api/v2/location-area/306/")))
                add(AreaEncountersItem(LocationArea("kanto-route-14-area", "https://pokeapi.co/api/v2/location-area/307/")))
                add(AreaEncountersItem(LocationArea("kanto-route-15-area", "https://pokeapi.co/api/v2/location-area/308/")))
                add(AreaEncountersItem(LocationArea("kanto-sea-route-21-area", "https://pokeapi.co/api/v2/location-area/312/")))
                add(AreaEncountersItem(LocationArea("kanto-route-24-area", "https://pokeapi.co/api/v2/location-area/314/")))
                add(AreaEncountersItem(LocationArea("kanto-route-25-area", "https://pokeapi.co/api/v2/location-area/315/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-north-towards-pewter-city", "https://pokeapi.co/api/v2/location-area/320/")))
                add(AreaEncountersItem(LocationArea("viridian-forest-area", "https://pokeapi.co/api/v2/location-area/321/")))
                add(AreaEncountersItem(LocationArea("berry-forest-area", "https://pokeapi.co/api/v2/location-area/495/")))
                add(AreaEncountersItem(LocationArea("bond-bridge-area", "https://pokeapi.co/api/v2/location-area/515/")))
                add(AreaEncountersItem(LocationArea("five-isle-meadow-area", "https://pokeapi.co/api/v2/location-area/519/")))
                add(AreaEncountersItem(LocationArea("kalos-route-2-area", "https://pokeapi.co/api/v2/location-area/713/")))
                add(AreaEncountersItem(LocationArea("kalos-route-3-area", "https://pokeapi.co/api/v2/location-area/714/")))
                add(AreaEncountersItem(LocationArea("johto-safari-zone-zone-forest", "https://pokeapi.co/api/v2/location-area/817/")))
            },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("johto-route-37-area", "https://pokeapi.co/api/v2/location-area/210/")))
                add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
                add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
                add(AreaEncountersItem(LocationArea("johto-route-43-area", "https://pokeapi.co/api/v2/location-area/241/")))
                add(AreaEncountersItem(LocationArea("kanto-route-12-area", "https://pokeapi.co/api/v2/location-area/276/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
                add(AreaEncountersItem(LocationArea("kanto-route-5-area", "https://pokeapi.co/api/v2/location-area/299/")))
                add(AreaEncountersItem(LocationArea("kanto-route-6-area", "https://pokeapi.co/api/v2/location-area/300/")))
                add(AreaEncountersItem(LocationArea("kanto-route-7-area", "https://pokeapi.co/api/v2/location-area/301/")))
                add(AreaEncountersItem(LocationArea("kanto-route-8-area", "https://pokeapi.co/api/v2/location-area/302/")))
                add(AreaEncountersItem(LocationArea("kanto-route-11-area", "https://pokeapi.co/api/v2/location-area/305/")))
                add(AreaEncountersItem(LocationArea("kanto-route-13-area", "https://pokeapi.co/api/v2/location-area/306/")))
                add(AreaEncountersItem(LocationArea("kanto-route-14-area", "https://pokeapi.co/api/v2/location-area/307/")))
                add(AreaEncountersItem(LocationArea("kanto-route-15-area", "https://pokeapi.co/api/v2/location-area/308/")))
                add(AreaEncountersItem(LocationArea("kanto-sea-route-21-area", "https://pokeapi.co/api/v2/location-area/312/")))
                add(AreaEncountersItem(LocationArea("kanto-route-24-area", "https://pokeapi.co/api/v2/location-area/314/")))
                add(AreaEncountersItem(LocationArea("kanto-route-25-area", "https://pokeapi.co/api/v2/location-area/315/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-north-towards-pewter-city", "https://pokeapi.co/api/v2/location-area/320/")))
                add(AreaEncountersItem(LocationArea("viridian-forest-area", "https://pokeapi.co/api/v2/location-area/321/")))
                add(AreaEncountersItem(LocationArea("berry-forest-area", "https://pokeapi.co/api/v2/location-area/495/")))
                add(AreaEncountersItem(LocationArea("bond-bridge-area", "https://pokeapi.co/api/v2/location-area/515/")))
                add(AreaEncountersItem(LocationArea("five-isle-meadow-area", "https://pokeapi.co/api/v2/location-area/519/")))
                add(AreaEncountersItem(LocationArea("johto-safari-zone-zone-plains", "https://pokeapi.co/api/v2/location-area/815/")))
                add(AreaEncountersItem(LocationArea("johto-safari-zone-zone-mountain", "https://pokeapi.co/api/v2/location-area/820/")))
            },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("alola-route-10-area", "https://pokeapi.co/api/v2/location-area/1055/")))
            },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("sinnoh-route-225-area", "https://pokeapi.co/api/v2/location-area/173/")))
                add(AreaEncountersItem(LocationArea("sinnoh-sea-route-226-area", "https://pokeapi.co/api/v2/location-area/182/")))
                add(AreaEncountersItem(LocationArea("johto-route-29-area", "https://pokeapi.co/api/v2/location-area/185/")))
                add(AreaEncountersItem(LocationArea("johto-route-30-area", "https://pokeapi.co/api/v2/location-area/187/")))
                add(AreaEncountersItem(LocationArea("johto-route-31-area", "https://pokeapi.co/api/v2/location-area/188/")))
                add(AreaEncountersItem(LocationArea("sprout-tower-2f", "https://pokeapi.co/api/v2/location-area/190/")))
                add(AreaEncountersItem(LocationArea("sprout-tower-3f", "https://pokeapi.co/api/v2/location-area/191/")))
                add(AreaEncountersItem(LocationArea("johto-route-32-area2", "https://pokeapi.co/api/v2/location-area/192/")))
                add(AreaEncountersItem(LocationArea("union-cave-1f", "https://pokeapi.co/api/v2/location-area/198/")))
                add(AreaEncountersItem(LocationArea("union-cave-b1f", "https://pokeapi.co/api/v2/location-area/199/")))
                add(AreaEncountersItem(LocationArea("union-cave-b2f", "https://pokeapi.co/api/v2/location-area/200/")))
                add(AreaEncountersItem(LocationArea("johto-route-33-area", "https://pokeapi.co/api/v2/location-area/201/")))
                add(AreaEncountersItem(LocationArea("johto-route-34-area", "https://pokeapi.co/api/v2/location-area/205/")))
                add(AreaEncountersItem(LocationArea("burned-tower-1f", "https://pokeapi.co/api/v2/location-area/212/")))
                add(AreaEncountersItem(LocationArea("burned-tower-b1f", "https://pokeapi.co/api/v2/location-area/213/")))
                add(AreaEncountersItem(LocationArea("bell-tower-2f", "https://pokeapi.co/api/v2/location-area/214/")))
                add(AreaEncountersItem(LocationArea("bell-tower-3f", "https://pokeapi.co/api/v2/location-area/215/")))
                add(AreaEncountersItem(LocationArea("bell-tower-4f", "https://pokeapi.co/api/v2/location-area/216/")))
                add(AreaEncountersItem(LocationArea("bell-tower-5f", "https://pokeapi.co/api/v2/location-area/217/")))
                add(AreaEncountersItem(LocationArea("bell-tower-6f", "https://pokeapi.co/api/v2/location-area/218/")))
                add(AreaEncountersItem(LocationArea("bell-tower-7f", "https://pokeapi.co/api/v2/location-area/219/")))
                add(AreaEncountersItem(LocationArea("bell-tower-8f", "https://pokeapi.co/api/v2/location-area/220/")))
                add(AreaEncountersItem(LocationArea("bell-tower-9f", "https://pokeapi.co/api/v2/location-area/221/")))
                add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
                add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
                add(AreaEncountersItem(LocationArea("johto-route-42-area", "https://pokeapi.co/api/v2/location-area/236/")))
                add(AreaEncountersItem(LocationArea("mt-mortar-1f", "https://pokeapi.co/api/v2/location-area/237/")))
                add(AreaEncountersItem(LocationArea("mt-mortar-lower-cave", "https://pokeapi.co/api/v2/location-area/238/")))
                add(AreaEncountersItem(LocationArea("mt-mortar-b1f", "https://pokeapi.co/api/v2/location-area/240/")))
                add(AreaEncountersItem(LocationArea("johto-route-46-area", "https://pokeapi.co/api/v2/location-area/252/")))
                add(AreaEncountersItem(LocationArea("bell-tower-10f", "https://pokeapi.co/api/v2/location-area/268/")))
                add(AreaEncountersItem(LocationArea("unknown-all-rattata-area", "https://pokeapi.co/api/v2/location-area/275/")))
                add(AreaEncountersItem(LocationArea("kanto-route-1-area", "https://pokeapi.co/api/v2/location-area/295/")))
                add(AreaEncountersItem(LocationArea("kanto-route-2-south-towards-viridian-city", "https://pokeapi.co/api/v2/location-area/296/")))
                add(AreaEncountersItem(LocationArea("kanto-route-3-area", "https://pokeapi.co/api/v2/location-area/297/")))
                add(AreaEncountersItem(LocationArea("kanto-route-4-area", "https://pokeapi.co/api/v2/location-area/298/")))
                add(AreaEncountersItem(LocationArea("kanto-route-5-area", "https://pokeapi.co/api/v2/location-area/299/")))
                add(AreaEncountersItem(LocationArea("kanto-route-6-area", "https://pokeapi.co/api/v2/location-area/300/")))
                add(AreaEncountersItem(LocationArea("kanto-route-7-area", "https://pokeapi.co/api/v2/location-area/301/")))
                add(AreaEncountersItem(LocationArea("kanto-route-8-area", "https://pokeapi.co/api/v2/location-area/302/")))
                add(AreaEncountersItem(LocationArea("kanto-route-9-area", "https://pokeapi.co/api/v2/location-area/303/")))
                add(AreaEncountersItem(LocationArea("kanto-route-10-area", "https://pokeapi.co/api/v2/location-area/304/")))
                add(AreaEncountersItem(LocationArea("kanto-route-11-area", "https://pokeapi.co/api/v2/location-area/305/")))
                add(AreaEncountersItem(LocationArea("kanto-route-16-area", "https://pokeapi.co/api/v2/location-area/309/")))
                add(AreaEncountersItem(LocationArea("kanto-route-17-area", "https://pokeapi.co/api/v2/location-area/310/")))
                add(AreaEncountersItem(LocationArea("kanto-route-18-area", "https://pokeapi.co/api/v2/location-area/311/")))
                add(AreaEncountersItem(LocationArea("castelia-sewers-area", "https://pokeapi.co/api/v2/location-area/671/")))
                add(AreaEncountersItem(LocationArea("castelia-sewers-unknown-area-38", "https://pokeapi.co/api/v2/location-area/672/")))
                add(AreaEncountersItem(LocationArea("relic-passage-castelia-sewers-entrance", "https://pokeapi.co/api/v2/location-area/692/")))
                add(AreaEncountersItem(LocationArea("alola-route-1-south", "https://pokeapi.co/api/v2/location-area/1037/")))
            },
            AreaEncounters().apply {
                add(AreaEncountersItem(LocationArea("sinnoh-route-225-area", "https://pokeapi.co/api/v2/location-area/173/")))
                add(AreaEncountersItem(LocationArea("sinnoh-sea-route-226-area", "https://pokeapi.co/api/v2/location-area/182/")))
                add(AreaEncountersItem(LocationArea("union-cave-b2f", "https://pokeapi.co/api/v2/location-area/200/")))
                add(AreaEncountersItem(LocationArea("burned-tower-1f", "https://pokeapi.co/api/v2/location-area/212/")))
                add(AreaEncountersItem(LocationArea("johto-route-38-area", "https://pokeapi.co/api/v2/location-area/222/")))
                add(AreaEncountersItem(LocationArea("johto-route-39-area", "https://pokeapi.co/api/v2/location-area/223/")))
                add(AreaEncountersItem(LocationArea("johto-route-42-area", "https://pokeapi.co/api/v2/location-area/236/")))
                add(AreaEncountersItem(LocationArea("mt-mortar-1f", "https://pokeapi.co/api/v2/location-area/237/")))
                add(AreaEncountersItem(LocationArea("mt-mortar-lower-cave", "https://pokeapi.co/api/v2/location-area/238/")))
                add(AreaEncountersItem(LocationArea("mt-mortar-upper-cave", "https://pokeapi.co/api/v2/location-area/239/")))
                add(AreaEncountersItem(LocationArea("mt-mortar-b1f", "https://pokeapi.co/api/v2/location-area/240/")))
                add(AreaEncountersItem(LocationArea("johto-route-43-area", "https://pokeapi.co/api/v2/location-area/241/")))
                add(AreaEncountersItem(LocationArea("johto-route-47-area", "https://pokeapi.co/api/v2/location-area/255/")))
                add(AreaEncountersItem(LocationArea("kanto-route-26-area", "https://pokeapi.co/api/v2/location-area/287/")))
                add(AreaEncountersItem(LocationArea("kanto-route-27-area", "https://pokeapi.co/api/v2/location-area/288/")))
                add(AreaEncountersItem(LocationArea("kanto-route-1-area", "https://pokeapi.co/api/v2/location-area/295/")))
                add(AreaEncountersItem(LocationArea("kanto-route-3-area", "https://pokeapi.co/api/v2/location-area/297/")))
                add(AreaEncountersItem(LocationArea("kanto-route-4-area", "https://pokeapi.co/api/v2/location-area/298/")))
                add(AreaEncountersItem(LocationArea("kanto-route-6-area", "https://pokeapi.co/api/v2/location-area/300/")))
                add(AreaEncountersItem(LocationArea("kanto-route-7-area", "https://pokeapi.co/api/v2/location-area/301/")))
                add(AreaEncountersItem(LocationArea("kanto-route-9-area", "https://pokeapi.co/api/v2/location-area/303/")))
                add(AreaEncountersItem(LocationArea("kanto-route-10-area", "https://pokeapi.co/api/v2/location-area/304/")))
                add(AreaEncountersItem(LocationArea("kanto-route-11-area", "https://pokeapi.co/api/v2/location-area/305/")))
                add(AreaEncountersItem(LocationArea("kanto-route-16-area", "https://pokeapi.co/api/v2/location-area/309/")))
                add(AreaEncountersItem(LocationArea("kanto-route-17-area", "https://pokeapi.co/api/v2/location-area/310/")))
                add(AreaEncountersItem(LocationArea("kanto-route-18-area", "https://pokeapi.co/api/v2/location-area/311/")))
                add(AreaEncountersItem(LocationArea("kanto-sea-route-21-area", "https://pokeapi.co/api/v2/location-area/312/")))
                add(AreaEncountersItem(LocationArea("tohjo-falls-area", "https://pokeapi.co/api/v2/location-area/316/")))
                add(AreaEncountersItem(LocationArea("pokemon-mansion-1f", "https://pokeapi.co/api/v2/location-area/341/")))
                add(AreaEncountersItem(LocationArea("pokemon-mansion-2f", "https://pokeapi.co/api/v2/location-area/342/")))
                add(AreaEncountersItem(LocationArea("pokemon-mansion-3f", "https://pokeapi.co/api/v2/location-area/343/")))
                add(AreaEncountersItem(LocationArea("pokemon-mansion-b1f", "https://pokeapi.co/api/v2/location-area/344/")))
                add(AreaEncountersItem(LocationArea("dreamyard-area", "https://pokeapi.co/api/v2/location-area/579/")))
                add(AreaEncountersItem(LocationArea("dreamyard-b1f", "https://pokeapi.co/api/v2/location-area/580/")))
                add(AreaEncountersItem(LocationArea("strange-house-1f", "https://pokeapi.co/api/v2/location-area/690/")))
                add(AreaEncountersItem(LocationArea("strange-house-b1f", "https://pokeapi.co/api/v2/location-area/691/")))
                add(AreaEncountersItem(LocationArea("relic-passage-relic-castle-entrance", "https://pokeapi.co/api/v2/location-area/693/")))
                add(AreaEncountersItem(LocationArea("johto-safari-zone-zone-plains", "https://pokeapi.co/api/v2/location-area/815/")))
                add(AreaEncountersItem(LocationArea("johto-safari-zone-zone-mountain", "https://pokeapi.co/api/v2/location-area/820/")))
                add(AreaEncountersItem(LocationArea("heahea-beach-area", "https://pokeapi.co/api/v2/location-area/1087/")))
            }
        )


        val expectedList: List<PokemonModel> = listOf(
            PokemonModel(1,64,7,true, listOf("Cerulean city area", "Pallet town area", "Lumiose city area"),"Bulbasaur",1,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),69),
            PokemonModel(2,142,10,true, emptyList(),"Ivysaur",2,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"),130),
            PokemonModel(3,263,20,true, emptyList(),"Venusaur",3,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png"),1000),
            PokemonModel(4,62,6,true, listOf("Pallet town area", "Kanto route 24 area", "Lumiose city area"),"Charmander",5,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"),85),
            PokemonModel(5, 142, 11, true, emptyList(), "Charmeleon", 6,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png"), 190),
            PokemonModel(6, 267, 17, true, emptyList(), "Charizard", 7,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png"), 905),
            PokemonModel(7, 63, 5, true, listOf("Vermilion city area", "Pallet town area", "Lumiose city area"), "Squirtle", 10,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png"), 90),
            PokemonModel(8, 142, 10, true, emptyList(), "Wartortle", 11,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/8.png"), 225),
            PokemonModel(9, 265, 16, true, emptyList(), "Blastoise", 12,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png"), 855),
            PokemonModel(10, 39, 3, true, listOf("Eterna forest area", "Sinnoh route 204 south towards jubilife city",
                "Sinnoh route 204 north towards floaroma town"), "Caterpie", 14,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png"), 29),
            PokemonModel(11, 72, 7, true, listOf("Eterna forest area", "Johto route 30 area", "Johto route 31 area"), "Metapod", 15,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/11.png"), 99),
            PokemonModel(12, 198, 11, true, listOf("Ilex forest area", "Johto route 34 area", "Johto route 35 area"), "Butterfree", 16,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/12.png"), 320),
            PokemonModel(13, 39, 3, true, listOf("Eterna forest area", "Sinnoh route 204 south towards jubilife city",
                "Sinnoh route 204 north towards floaroma town"), "Weedle", 17,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/13.png"), 32),
            PokemonModel(14, 72, 6, true, listOf("Eterna forest area", "Johto route 30 area", "Johto route 31 area"), "Kakuna", 18,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/14.png"), 100),
            PokemonModel(15, 178, 10, true, listOf("Ilex forest area", "Johto route 34 area", "Johto route 35 area"), "Beedrill", 19,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/15.png"), 295),
            PokemonModel(16, 50, 3, true, listOf("Sinnoh route 229 area", "Johto route 29 area", "Johto route 30 area"), "Pidgey", 21,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/16.png"), 18),
            PokemonModel(17, 122, 11, true, listOf("Johto route 37 area", "Johto route 38 area", "Johto route 39 area"), "Pidgeotto", 22,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/17.png"), 300),
            PokemonModel(18, 216, 15, true, listOf("Alola route 10 area"), "Pidgeot", 23,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/18.png"), 395),
            PokemonModel(19, 51, 3, true, listOf("Sinnoh route 225 area", "Sinnoh sea route 226 area", "Johto route 29 area"), "Rattata", 25,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/19.png"), 35),
            PokemonModel(20, 145, 7, true, listOf("Sinnoh route 225 area", "Sinnoh sea route 226 area", "Union cave b2f"), "Raticate", 27,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/20.png"), 185)
        )

        //mockk
        coEvery { restService.getPokemonList() } returns Response.success(PokemonList(null, null, null, expectedInitialList))
        val names = expectedInitialList.map { it.name }.filterNotNull()

        expectedPokemonList.forEach {pokemon ->
            names.map { coEvery { restService.getPokemon(it) } returns Response.success(pokemon) }
        }

        val expectedLocations = arrayListOf<List<String>>()

        for (id in 1..20) {
            coEvery { restService.getLocationAreaEncounters(id) } returns Response.success(expectedAreaEncounters[id - 1])
            val locations =
                if (expectedAreaEncounters[id - 1].isNotEmpty())
                    expectedAreaEncounters[id - 1].map { it.locationArea.name } else emptyList()
            expectedLocations.add(locations)
        }

        val modelsList = expectedPokemonList.map {pokemon ->
            val newName =  pokemon.name!!.replaceFirstChar { it.uppercase() }
            val locations = repository.createLocationsListWithSizeBounded(expectedLocations[pokemon.id!! - 1])
            val model = PokemonModel(
                pokemon.id!!, pokemon.baseExperience!!,pokemon.height!!, pokemon.isDefault!!,
                locations, newName, pokemon.order!!, pokemon.sprites!!, pokemon.weight!!
            )
            model
        }


        val result = repository.getPokemonList()

        Truth.assertThat(modelsList).isEqualTo(expectedList)
        Truth.assertThat(result).isEqualTo(modelsList)
    }

    @Test
    fun getInitialListTest_successfulResponse_emptyList_returnEmptyList() = runBlocking {
        coEvery { restService.getPokemonList() } returns Response.success(PokemonList(null, null, null, emptyList()))

        val result = repository.getInitialList()

        Truth.assertThat(result).isEqualTo(emptyList<PokemonList>())
    }

    @Test
    fun getInitialListTest_errorResponse_emptyList_returnEmptyList() = runBlocking {
        val errorResponse = Response.error<PokemonList>(
            404,
            ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                "{\"error\": \"Not found\"}"
            ))

        coEvery { restService.getPokemonList() } returns errorResponse

        val result = repository.getInitialList()

        Truth.assertThat(result).isEqualTo(emptyList<PokemonList>())
    }

    @Test
    fun getInitialListTest_successfulResponse_PokemonList_returnCorrectList() = runBlocking {
        val expectedList: List<Pokemon> = listOf(
            Pokemon(null,null,null,null,null,"Bulbasaur",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Ivysaur",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Venusaur",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Charmander",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Charmeleon",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Charizard",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Squirtle",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Wartortle",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Blastoise",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Caterpie",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Metapod",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Butterfree",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Weedle",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Kakuna",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Beedrill",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Pidgey",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Pidgeotto",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Pidgeot",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Rattata",null, Sprites(null),null),
            Pokemon(null,null,null,null,null,"Raticate",null, Sprites(null),null)
        )

        coEvery { restService.getPokemonList() } returns Response.success(PokemonList(null, null, null, expectedList))

        val result = repository.getInitialList()

        Truth.assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun createLocationsListWithSizeBoundedTest_listOfSize3_returnCorrectList() {
        val result = repository.createLocationsListWithSizeBounded(listOf("location-A", "location-B", "location-C"))

        Truth.assertThat(result).isEqualTo(listOf("Location A", "Location B", "Location C"))
    }

    @Test
    fun createLocationsListWithSizeBoundedTest_listOfSizeGreaterThan3_returnCorrectList() {
        val result = repository.createLocationsListWithSizeBounded(listOf("location-A", "location-B", "location-C", "location-D", "location-E"))

        Truth.assertThat(result).isEqualTo(listOf("Location A", "Location B", "Location C"))
    }

    @Test
    fun createLocationsListWithSizeBoundedTest_listOfSize2_returnCorrectList() {
        val result = repository.createLocationsListWithSizeBounded(listOf("location-A", "location-B"))

        Truth.assertThat(result).isEqualTo(listOf("Location A", "Location B"))
    }

    @Test
    fun createLocationsListWithSizeBoundedTest_listOfSize1_returnCorrectList() {
        val result = repository.createLocationsListWithSizeBounded(listOf("location-A"))

        Truth.assertThat(result).isEqualTo(listOf("Location A"))
    }

    @Test
    fun createLocationsListWithSizeBoundedTest_emptyList_returnEmptyList() {
        val result = repository.createLocationsListWithSizeBounded(emptyList())

        Truth.assertThat(result).isEqualTo(emptyList<String>())
    }

    @Test
    fun processLocationTest_wellFormattedName_returnCorrectNewLocation() {
        val result = repository.processLocation("location-A")

        Truth.assertThat(result).isEqualTo("Location A")
    }

    @Test
    fun processLocationTest_emptyList_returnNoLocation() {
        val result = repository.processLocation("")

        Truth.assertThat(result).isEqualTo("")
    }
}