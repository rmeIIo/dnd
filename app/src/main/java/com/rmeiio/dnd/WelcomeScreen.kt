package com.rmeiio.dnd

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun WelcomeScreen(navController: NavController, context: Context) {

    val databaseHelper = DatabaseHelper(context)

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

        Text(
            "Bem-vindo à Criação de Personagem",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.End).padding(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (databaseHelper.hasCharacter()) {
                    navController.navigate("character_list")
                } else {
                    navController.navigate("name_and_class")
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                "COMEÇAR",
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
    }
    }
}