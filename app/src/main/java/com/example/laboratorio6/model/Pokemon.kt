package com.example.laboratorio6.model

data class Pokemon(
    val name: String,
    val url: String
) {
    // Extraer el ID del Pokémon desde la URL
    val imageUrl: String
        get() {
            val pokemonId = url.split("/").dropLast(1).last()
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
        }
}

