package com.example.compose.pokemonapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.compose.pokemonapp.R
import com.example.compose.pokemonapp.domain.model.PokemonModel
import com.example.compose.pokemonapp.presentation.viewmodel.PokemonViewModel
import com.example.compose.pokemonapp.presentation.viewmodel.PokemonViewModelFactory
import com.example.compose.pokemonapp.presentation.theme.PokemonAppTheme
import com.example.compose.pokemonapp.presentation.viewmodel.PokemonUiState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var factory: PokemonViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokemonAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PokemonApp(factory = factory) { pokemon ->
                        startActivity(DetailsActivity.intent(this, pokemon))
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonApp(factory: PokemonViewModelFactory, modifier: Modifier = Modifier, viewModel: PokemonViewModel = viewModel(factory = factory), onClick: (PokemonModel) -> Unit) {
    Scaffold(
        topBar = { PokemonTopBar() },
        modifier = modifier.fillMaxSize()
    ) {values ->
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreen(
                pokemonUiState = viewModel.pokemonUiState,
                contentPadding = values,
                onClick = onClick,
                retry = viewModel::getPokemonList
            )
        }
    }
}

@Composable
fun HomeScreen(pokemonUiState: PokemonUiState, onClick: (PokemonModel) -> Unit, retry: () -> Unit, contentPadding: PaddingValues = PaddingValues(0.dp),
               modifier: Modifier = Modifier) {
    when (pokemonUiState) {
        is PokemonUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PokemonUiState.Success -> PokemonList(pokemonList = pokemonUiState.pokemonList, onClick = onClick, contentPadding = contentPadding)
        is PokemonUiState.Error -> ErrorScreen(retry = retry, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PokemonList(pokemonList: List<PokemonModel>, contentPadding: PaddingValues = PaddingValues(0.dp), onClick: (PokemonModel) -> Unit,
                modifier: Modifier = Modifier) {
    LazyColumn(contentPadding = contentPadding) {
        items(pokemonList) {
            PokemonItem(pokemon = it, onclick = onClick, modifier = modifier.padding(8.dp))
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokemonModel, onclick: (PokemonModel) -> Unit, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(pokemon.sprites.frontDefault)
            .scale(Scale.FILL)
            .size(coil.size.Size.ORIGINAL)
            .build(),
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img)
    )

    Card(
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 20.dp,
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
            .size(350.dp, 200.dp)
            .fillMaxSize()
            .clickable { onclick(pokemon) }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painter,
                contentDescription = "pokemonIcon",
                modifier = modifier
                    .size(150.dp)
                    .padding(10.dp)
                    .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(
                text = pokemon.name,
                modifier = modifier.align(Alignment.CenterVertically)
            )
        }
    }
}


@Composable
fun ErrorScreen(retry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.connection_error)
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retry) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.title_activity_main),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.pokebola),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        },
        modifier = modifier
            .padding(8.dp)
            .size(380.dp, 130.dp)
    )
}