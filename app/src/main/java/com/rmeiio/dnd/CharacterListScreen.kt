package com.rmeiio.dnd

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.time.format.TextStyle

@Composable
fun CharacterListScreen(navController: NavController, context: Context) {
    val databaseHelper = DatabaseHelper(context)
    val characterList = remember { databaseHelper.getAllCharacters() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color(0xFF121212)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AsyncImage(
            model = "https://documentdesignfall17.wordpress.com/wp-content/uploads/2017/08/dungeons__dragons_5th_edition_logo-svg.png?w=1200",
            contentDescription = "Dungeons and Dragons logo",
            modifier = Modifier
                .height(100.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            "Lista de Personagens",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        characterList.forEach { character ->
            CharacterItem(character = character, onClick = {
                navController.navigate("character_detail/${character.id}")
            })
            Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.height(8.dp))

    }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // Distribui o espaço entre os botões
        ) {
            Button(
                onClick = { navController.navigate("name_and_class") },
                colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    "Criar novo personagem!",
                    color = Color.White,
                    style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold)
                )
            }

            Button(
                onClick = { navController.navigate("welcome") },
                colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    "Voltar ao início",
                    color = Color.White,
                    style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        }
}
}

@Composable
fun CharacterItem(character: Character, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(Color.Red)
    ) {
        Text(
            text = "${character.name} - ${character.charClass} - ${character.race}",
            color = Color.White,
            style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold)
        )
    }
}