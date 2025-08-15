package com.example.gotchagames_mobdevtfinals

data class Genre(
    val id: Int,
    val name: String
)

data class Game(
    val id: Int,
    val name: String,
    val background_image: String?,
    val genreId: Int
)

data class GamesResponse(
    val results: List<Game>
)

data class GenresResponse(
    val results: List<Genre>
)