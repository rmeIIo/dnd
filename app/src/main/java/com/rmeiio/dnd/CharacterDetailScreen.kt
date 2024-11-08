package com.rmeiio.dnd

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun CharacterDetailScreen(navController: NavController, characterId : Int, context: Context) {
    val databaseHelper = DatabaseHelper(context)
    val character = databaseHelper.getCharacterById(characterId) // Adicione esse método no DatabaseHelper

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF121212)),
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

        if (character != null) {
            Text(
                text = "Nome: ${character.name}",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text("Classe: ${character.charClass}", color = Color.White)
            Text("Raça: ${character.race}", color = Color.White)
            Text("Força: ${character.attributes.forca}", color = Color.White)
            Text("Destreza: ${character.attributes.destreza}", color = Color.White)
            Text("Constituição: ${character.attributes.constituicao}", color = Color.White)
            Text("Inteligência: ${character.attributes.inteligencia}", color = Color.White)
            Text("Sabedoria: ${character.attributes.sabedoria}", color = Color.White)
            Text("Carisma: ${character.attributes.carisma}", color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de atualização
            Button(
                onClick = {
                    // Ação para atualizar o personagem (navegar para uma tela de edição, por exemplo)
                    navController.navigate("update_character/$characterId")
                },
                colors = ButtonDefaults.buttonColors(Color.Gray),
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Editar", color = Color.White)
            }

            // Botão de exclusão
            Button(
                onClick = {
                    databaseHelper.deleteCharacter(characterId)
                    navController.popBackStack() // Voltar para a lista de personagens após exclusão
                },
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Excluir", color = Color.White)
            }
        } else {
            Text(
                text = "Personagem não encontrado",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}