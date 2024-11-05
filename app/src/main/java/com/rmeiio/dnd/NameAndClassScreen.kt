package com.rmeiio.dnd

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rmeiio.dnd.model.Classe

@Composable
fun NameAndClassScreen(navController: NavController, context: Context) {
    var nome by remember{ mutableStateOf("") }
    var classe by remember{ mutableStateOf(Classe.values().first().name) }
    var isDropDownExpanded by remember { mutableStateOf(false) }

    val databaseHelper = DatabaseHelper(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Nome do personagem",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(start = 56.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = nome,
            onValueChange = {nome = it},
            label = { Text("Digite o nome do personagem") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            modifier = Modifier.width(280.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Selecione a classe",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(start = 56.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = classe,
            modifier = Modifier
                .width(280.dp)
                .clickable { isDropDownExpanded = true }
                .background(Color.White)
                .padding(16.dp),
            fontSize = 18.sp
        )


        DropdownMenu(
            expanded = isDropDownExpanded,
            onDismissRequest = { isDropDownExpanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            Classe.values().forEach { classeItem ->
                DropdownMenuItem(
                    onClick = {
                        classe = classeItem.name
                        isDropDownExpanded = false }
                ) {
                    Text(
                        text = classeItem.name,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                databaseHelper.insertCharacter(nome, classe)
                navController.navigate("race/${databaseHelper.getLastCharacterId()}")
            },
            colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                "PRÓXIMO",
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
    }
}
}
