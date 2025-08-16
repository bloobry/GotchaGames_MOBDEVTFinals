package com.example.gotchagames_mobdevtfinals

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GotchaGamesApiService {
    @GET("genres")
    suspend fun getGenres(
        @Query("key") apiKey: String
    ): GenresResponse

    @GET("games")
    suspend fun getGames(
        @Query("genres") genreId: Int,
        @Query("key") apiKey: String
    ): GamesResponse

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") gameId: Int,
        @Query("key") apiKey: String
    ): GameDetail
}
