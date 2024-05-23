package com.example.compose.pokemonapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.compose.pokemonapp.domain.model.PokemonModel
import java.io.Serializable

class DetailsActivity : ComponentActivity() {
    companion object {
        private const val pokemonId = "pokemon"

        fun intent(context: Context, pokemon: PokemonModel?) =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(pokemonId, pokemon)
            }
    }

    private val pokemon: PokemonModel by lazy {
        intent?.getSerializableExtra(pokemonId, Serializable::class.java) as PokemonModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowPokemonDetails(pokemon = pokemon)
        }
    }
}

@Composable
fun ShowPokemonDetails(pokemon: PokemonModel, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
    ) {
        ShowTopDetailsView(pokemon = pokemon)
        Spacer(modifier = modifier.height(10.dp))
        ShowDetailsList(pokemon = pokemon)
    }
}

@Composable
fun ShowTopDetailsView(pokemon: PokemonModel, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(pokemon.sprites.frontDefault)
            .scale(Scale.FILL)
            .build()
    )

    Card(
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 20.dp,
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
            .size(270.dp)
    ) {
        Column() {
            Image(
                painter = painter,
                contentDescription = "pokemon image",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(230.dp)
            )
            Text(
                text = pokemon.name,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun ShowDetailsList(pokemon: PokemonModel, modifier: Modifier = Modifier) {
    val isDefault = if (pokemon.isDefault) "Yes" else "No"

    ShowPokemonCharacteristics(
        pokemon = pokemon,
        isDefault = isDefault,
        modifier = modifier
    )
    ShowPokemonLocationsList(
        pokemon = pokemon,
        modifier = modifier
    )
}

@Composable
fun ShowPokemonCharacteristics(pokemon: PokemonModel, isDefault: String, modifier: Modifier = Modifier) {
    Text(
        text = "Characteristics:",
        fontSize = 20.sp
    )

    Column(
        modifier = modifier.padding(20.dp)
    ) {
        Row() {
            Text(
                text = "Order:",
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.6f)
                    .padding(8.dp)
            )
            Text(
                text = pokemon.order.toString(),
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.4f)
                    .padding(8.dp)
            )
        }
        Row() {
            Text(
                text = "Height:",
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.6f)
                    .padding(8.dp)
            )
            Text(
                text = pokemon.height.toString(),
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.4f)
                    .padding(8.dp)
            )
        }
        Row() {
            Text(
                text = "Weight:",
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.6f)
                    .padding(8.dp)
            )
            Text(
                text = pokemon.weight.toString(),
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.4f)
                    .padding(8.dp)
            )
        }
        Row() {
            Text(
                text = "Is Default:",
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.6f)
                    .padding(8.dp)
            )
            Text(
                text = isDefault,
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.4f)
                    .padding(8.dp)
            )
        }
        Row() {
            Text(
                text = "Base Experience:",
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.6f)
                    .padding(8.dp)
            )
            Text(
                text = pokemon.baseExperience.toString(),
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .weight(0.4f)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun ShowPokemonLocationsList(pokemon: PokemonModel, modifier: Modifier = Modifier) {
    Text(
        text = "Locations:",
        fontSize = 20.sp
    )
    if (pokemon.locations.isEmpty()) {
        Row(modifier = modifier.padding(20.dp)) {
            Text(
                text = "No results",
                textAlign = TextAlign.Justify,
                fontSize = 20.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .width(320.dp)
                    .padding(8.dp)
            )

        }
    } else {
        LazyColumn(modifier = modifier.padding(20.dp)) {
            items(pokemon.locations) {
                Text(
                    text = it,
                    textAlign = TextAlign.Justify,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .width(320.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}