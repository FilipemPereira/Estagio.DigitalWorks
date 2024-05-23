package com.example.compose.pokemonapp.repository.viewModel

import com.example.compose.pokemonapp.data.model.Sprites
import com.example.compose.pokemonapp.domain.model.PokemonModel
import com.example.compose.pokemonapp.repository.viewModel.FakeRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class PokemonViewModelTest {
    private lateinit var repository: FakeRepository

    @Before
    fun setUp(){
        repository = FakeRepository()
    }

    @Test
    fun getPokemonListTest() {
        val expectedList: List<PokemonModel> = listOf(
            PokemonModel(1,64,7,true,listOf("Cerulean city area", "Pallet town area", "Lumiose city area"),"Bulbasaur",1,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),69),
            PokemonModel(2,142,10,true,emptyList(),"Ivysaur",2,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"),130),
            PokemonModel(3,263,20,true,emptyList(),"Venusaur",3,
                Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png"),1000),
            PokemonModel(4,62,6,true,listOf("Pallet town area", "Kanto route 24 area", "Lumiose city area"),"Charmander",5,
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

        val result = repository.pokemonList

        assertEquals(result,expectedList)
    }
}