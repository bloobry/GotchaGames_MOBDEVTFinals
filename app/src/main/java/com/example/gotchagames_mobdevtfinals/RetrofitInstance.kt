package com.example.gotchagames_mobdevtfinals

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.rawg.io/api/"

    val api: GotchaGamesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GotchaGamesApiService::class.java)
    }
}
