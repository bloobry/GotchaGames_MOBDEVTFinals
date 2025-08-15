package com.example.gotchagames_mobdevtfinals

import retrofit2.http.GET
import retrofit2.http.Query

interface GotchaGamesApiService {
    @GET("genres")
    suspend fun getGenres(
        @Query("key") apiKey: String
    ): GenresResponse
}
