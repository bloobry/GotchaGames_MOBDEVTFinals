package com.example.gotchagames_mobdevtfinals

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GotchaGamesScreen(viewModel: GotchaGamesViewModel = viewModel()) {
    val genres by viewModel.genres.collectAsState()
    val games by viewModel.games.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedGenre by remember { mutableStateOf<Genre?>(null) }
    val apiKey = "5dcb58160817413e9e3a0d1be2402e55"

    // !!For checking, check logs if games are fetched - kurt (if di nagshshow up sa screen)!!
    LaunchedEffect(genres) {
        Log.d("GotchaGamesScreen", "Genres: $genres")
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gotcha Games") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Dropdown
            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = selectedGenre?.name ?: "Select Genre",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Genre") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Icon"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    if (genres.isNotEmpty()) {
                        genres.forEach { genre ->
                            DropdownMenuItem(
                                text = { Text(genre.name) },
                                onClick = {
                                    selectedGenre = genre
                                    expanded = false
                                    viewModel.fetchGames(genre.id, apiKey)
                                }
                            )
                        }
                    } else {
                        DropdownMenuItem(
                            text = { Text("No genres available") },
                            onClick = {}
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Games list
            LazyColumn {
                items(games) { game ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = game.name,
                                fontSize = 18.sp
                            )
                            game.background_image?.let { imageUrl ->
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
