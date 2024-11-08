package com.rmeiio.dnd

import CharacterEditScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databaseHelper = DatabaseHelper(this)
        val charactersExists = databaseHelper.hasCharacter()

        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                Surface(
                    modifier = Modifier
                        .background(Color(0xFF121212))
                        .fillMaxSize(),
                    color = Color(0xFF121212)
                ) {
                    NavHost(navController, startDestination = "welcome") {
                        composable("welcome") {WelcomeScreen(navController, this@MainActivity)}
                        composable("name_and_class") { NameAndClassScreen(navController, this@MainActivity) }
                        composable("character_list") { CharacterListScreen(navController, this@MainActivity) }
                        composable("race/{characterId}") { backStackEntry ->
                            val characterId = backStackEntry.arguments?.getString("characterId")?.toInt() ?: 0
                            RaceScreen(navController, characterId, this@MainActivity)
                        }
                        composable("attribute_distribution/{characterId}") { backStackEntry ->
                            val characterId = backStackEntry.arguments?.getString("characterId")?.toInt() ?: 0
                            AttributeDistributionScreen(navController, characterId, this@MainActivity)
                        }
                        composable("character_detail/{characterId}") { backStackEntry ->
                            val characterId = backStackEntry.arguments?.getString("characterId")?.toInt() ?: 0
                            CharacterDetailScreen(navController, characterId, this@MainActivity)
                        }
                        composable("update_character/{characterId}") { backStackEntry ->
                            val characterId = backStackEntry.arguments?.getString("characterId")?.toInt() ?: 0
                            CharacterEditScreen(navController, characterId, this@MainActivity)
                        }
                        composable("success") { SuccessScreen(navController) }
                    }
                }
            }
        }
    }
}