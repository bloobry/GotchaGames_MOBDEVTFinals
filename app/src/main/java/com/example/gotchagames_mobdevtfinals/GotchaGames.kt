package com.example.gotchagames_mobdevtfinals

data class Genre(
    val id: Int,
    val name: String,
    val slug: String,
    val image_background: String
)

data class GenresResponse(
    val results: List<Genre>
)

