package com.example.laboratorio6

import PokemonAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laboratorio6.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonRecyclerView = findViewById(R.id.pokemon_recycler_view)
        pokemonRecyclerView.layoutManager = LinearLayoutManager(this)

        // Hacer la solicitud a la API usando coroutines
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.apiService.getPokemonList(100)
            withContext(Dispatchers.Main) {
                pokemonRecyclerView.adapter = PokemonAdapter(response.results)
            }
        }
    }
}
