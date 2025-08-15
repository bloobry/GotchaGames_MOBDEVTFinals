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

    fun fetchGenres(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getGenres(apiKey)
                _genres.value = response.results
            } catch (e: Exception) {
                Log.e("GotchaGamesVM", "Error fetching genres", e)
            }
        }
    }
}
