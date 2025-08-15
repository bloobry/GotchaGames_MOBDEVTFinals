package com.example.gotchagames_mobdevtfinals

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GotchaGamesViewModel : ViewModel() {

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres: StateFlow<List<Genre>> = _genres

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchGenres(apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitInstance.api.getGenres(apiKey)
                _genres.value = response.results
                Log.d("GotchaGamesViewModel", "Fetched genres: ${response.results}") // Log fetched genres
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error occurred"
                Log.e("GotchaGamesViewModel", "Error fetching genres: ${e.message}") // Log error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchGames(genreId: Int, apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitInstance.api.getGames(genreId, apiKey)
                _games.value = response.results
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
