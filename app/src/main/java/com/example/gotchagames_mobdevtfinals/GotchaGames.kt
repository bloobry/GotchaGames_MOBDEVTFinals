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

data class GameDetail(
    val id: Int,
    val name: String,
    val description: String?,
    val released: String?,
    val background_image: String?,
    val background_image_additional: String?,
    val website: String?,
    val platforms: List<PlatformWrapper>?,
    val genres: List<Genre>?,
    val rating: Float?,
    val ratings: List<Rating>?
)

data class PlatformWrapper(
    val platform: Platform
)

data class Platform(
    val id: Int,
    val name: String
)

data class Rating(
    val id: Int,
    val title: String,
    val count: Int,
    val percent: Float
)
