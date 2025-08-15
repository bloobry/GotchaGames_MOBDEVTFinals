package com.example.gotchagames_mobdevtfinals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gotchagames_mobdevtfinals.ui.theme.GotchaGames_MOBDEVTFinalsTheme
import androidx.activity.viewModels



class MainActivity : ComponentActivity() {
    private val viewModel: GotchaGamesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GotchaGamesScreen(viewModel)
        }
        viewModel.fetchGenres("5dcb58160817413e9e3a0d1be2402e55")
    }
}

//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    GotchaGames_MOBDEVTFinalsTheme {
//        Greeting("Android")
//    }
//}