package com.example.laboratorio6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.laboratorio6.model.Pokemon
import com.example.laboratorio6.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PokemonListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen() {
    val pokemonList = remember { mutableStateListOf<Pokemon>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val response = RetrofitClient.apiService.getPokemonList(20)
            pokemonList.addAll(response.results)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar
        CenterAlignedTopAppBar(
            title = { Text("MainFragment") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFF762FFC),
                titleContentColor = Color.White
            )
        )

        // LazyColumn for Pokemon List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(pokemonList) { pokemon ->
                PokemonItem(pokemon) {
                    // Acción temporal cuando se haga clic (imprime un mensaje en consola)
                    println("Clicked on ${pokemon.name}")
                }
            }
        }
    }
}




@Composable
fun PokemonItem(pokemon: Pokemon, onClick: () -> Unit) {
    val pokemonId = pokemon.url
        .trimEnd('/')
        .split("/").last()

    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // Hacer el elemento clickeable
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = pokemon.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            BasicText(text = pokemon.name)
        }
    }
}
