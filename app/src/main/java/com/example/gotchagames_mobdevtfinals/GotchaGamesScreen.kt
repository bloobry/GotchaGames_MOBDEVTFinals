package com.example.gotchagames_mobdevtfinals

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import com.example.gotchagames_mobdevtfinals.ui.theme.PressStart2P
import com.example.gotchagames_mobdevtfinals.ui.theme.Calistoga
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GotchaGamesScreen(viewModel: GotchaGamesViewModel = viewModel()) {
    val genres by viewModel.genres.collectAsState()
    val games by viewModel.games.collectAsState()
    val selectedGame by viewModel.selectedGame.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedGenre by remember { mutableStateOf<Genre?>(null) }
    val apiKey = "5dcb58160817413e9e3a0d1be2402e55"

    // !! For checking, check logs if games are fetched - kurt !!
    LaunchedEffect(genres) {
        Log.d("GotchaGamesScreen", "Genres: $genres")
    }

    if (selectedGame == null) {
        // Main screen
        Surface(
            color = Color(0xFF0E0728), // bg color
            modifier = Modifier.fillMaxSize()
        ) {

            Scaffold(
                containerColor = Color.Transparent, //transparent
                topBar = {
                    TopAppBar(
                        title = { Text("GOTCHA GAMES",  fontFamily = PressStart2P, color = Color.White) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent
                        )
                    )
                }
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
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
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(games) { game ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp)
                                    .clickable { viewModel.fetchGameDetails(game.id, apiKey) },
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF0E0728))
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = game.background_image,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(70.dp)
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = game.name,
                                        color = Color.White,
                                        fontFamily = PressStart2P,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                            Divider(                                   // <- thin line between cards
                                color = Color(0xFF7A7A86),
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    } else {
        // Details Screen
        GameDetailsScreen(
            gameDetail = selectedGame!!,
            onBack = { viewModel.clearSelectedGame() }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsScreen(gameDetail: GameDetail, onBack: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0E0728)
    ){
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("GOTCHA GAMES", fontFamily = PressStart2P, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,  // background transparent
                        scrolledContainerColor = Color.Transparent, // stays transparent when scrolling
                ))
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                gameDetail.background_image?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }

                // Additional image (optional if wala na oras for the slideshow)
                gameDetail.background_image_additional?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }


                Spacer(modifier = Modifier.height(8.dp))

//              TITLE
                Text(text = gameDetail.name,
                    fontSize = 28.sp,
                    fontFamily = Calistoga,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(2.dp))

//              GENRES
                FlowRow(
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 2.dp,
                    modifier = Modifier.padding(4.dp)
                        .fillMaxWidth(),
                    mainAxisAlignment = FlowMainAxisAlignment.Center
                ) {
                    gameDetail.genres?.forEach{ genre ->
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color(0xFFE23BA5),
                            modifier = Modifier.padding(8.dp)
                                .width(120.dp)
                        ) {
                            Text(text = genre.name,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
                        }
                    }
                }

//                Text(text = "Released: ${gameDetail.released ?: "N/A"}")
//                Text(text = "Rating: ${gameDetail.rating ?: 0f}")

                Spacer(modifier = Modifier.height(8.dp))

                gameDetail.description?.let {
                    val cleanDesc = it
                        .replace(Regex("&#39;"), "'")
                        .replace(Regex("<br\\s*/?>"), "\n\n")
                        .replace(Regex("<p>"), "")
                        .replace(Regex("</p>"), "")
                    Text(text = cleanDesc,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                gameDetail.website?.let {
                    Text(text = "Website: $it", color = Color.Blue)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Genres:")
                gameDetail.genres?.forEach { genre ->
                    Text(text = "- ${genre.name}")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Platforms:")
                gameDetail.platforms?.forEach { wrapper ->
                    Text(text = "- ${wrapper.platform.name}")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Ratings:")
                gameDetail.ratings?.forEach { rating ->
                    Text(text = "${rating.title}: ${rating.percent}%")
                }
            }
        }
    }

}
