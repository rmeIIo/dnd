import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rmeiio.dnd.AttributeRow
import com.rmeiio.dnd.DatabaseHelper
import com.rmeiio.dnd.model.Classe
import com.rmeiio.dnd.model.Raca

@Composable
fun CharacterEditScreen(navController: NavController, characterId : Int, context: Context) {
    val databaseHelper = DatabaseHelper(context)

    var nome by remember { mutableStateOf("") }
    var classe by remember { mutableStateOf("") }
    var raca by remember { mutableStateOf("") }
    var forca by remember { mutableStateOf(8) }
    var destreza by remember { mutableStateOf(8) }
    var constituicao by remember { mutableStateOf(8) }
    var inteligencia by remember { mutableStateOf(8) }
    var sabedoria by remember { mutableStateOf(8) }
    var carisma by remember { mutableStateOf(8) }
    var pontosRestantes by remember { mutableStateOf(27) }

    var isRaceDropdownExpanded by remember { mutableStateOf(false) }
    var isClassDropdownExpanded by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        val character = databaseHelper.getCharacterById(characterId)
        if (character != null) {
            nome = character.name
            classe = character.charClass
            raca = character.race
            val attributes = character.attributes
            forca = attributes.forca
            destreza = attributes.destreza
            constituicao = attributes.constituicao
            inteligencia = attributes.inteligencia
            sabedoria = attributes.sabedoria
            carisma = attributes.carisma
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Personagem") },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            modifier = Modifier.width(280.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Seleção de Classe
        Text("Classe")
        Text(
            text = classe,
            modifier = Modifier
                .width(280.dp)
                .clickable { isClassDropdownExpanded = true }
                .background(Color.White)
                .padding(16.dp),
            fontSize = 18.sp
        )

        DropdownMenu(
            expanded = isClassDropdownExpanded,
            onDismissRequest = { isClassDropdownExpanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            Classe.values().forEach { classeItem ->
                androidx.compose.material.DropdownMenuItem(
                    onClick = {
                        classe = classeItem.name
                        isClassDropdownExpanded = false
                    }
                ) {
                    androidx.compose.material3.Text(
                        text = classeItem.name,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Seleção de Raça
        Text("Raça")
        Text(
            text = raca,
            modifier = Modifier
                .width(280.dp)
                .clickable { isRaceDropdownExpanded = true }
                .background(Color.White)
                .padding(16.dp),
            fontSize = 18.sp
        )

        DropdownMenu(
            expanded = isRaceDropdownExpanded,
            onDismissRequest = { isRaceDropdownExpanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            Raca.values().forEach { racaItem ->
                androidx.compose.material.DropdownMenuItem(
                    onClick = {
                        raca = racaItem.name
                        isRaceDropdownExpanded = false
                    }
                ) {
                    androidx.compose.material3.Text(
                        text = racaItem.name,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Atributos
        Text("Distribuição de Atributos")
        AttributeRow("Força", forca, { if (forca < 15) forca++ }, { if (forca > 8) forca-- })
        AttributeRow("Destreza", destreza, { if (destreza < 15) destreza++ }, { if (destreza > 8) destreza-- })
        AttributeRow("Constituição", constituicao, { if (constituicao < 15) constituicao++ }, { if (constituicao > 8) constituicao-- })
        AttributeRow("Inteligência", inteligencia, { if (inteligencia < 15) inteligencia++ }, { if (inteligencia > 8) inteligencia-- })
        AttributeRow("Sabedoria", sabedoria, { if (sabedoria < 15) sabedoria++ }, { if (sabedoria > 8) sabedoria-- })
        AttributeRow("Carisma", carisma, { if (carisma < 15) carisma++ }, { if (carisma > 8) carisma-- })

        Spacer(modifier = Modifier.height(32.dp))

        // Botão de salvar alterações
        Button(
            onClick = {
                databaseHelper.updateCharacter(
                    characterId, nome, classe, raca
                )
                databaseHelper.updateCharacterAttributes(
                    characterId, forca, destreza, constituicao, inteligencia, sabedoria, carisma
                )
                navController.navigate("success")
            },
            colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("SALVAR ALTERAÇÕES", color = Color.White)
        }
    }
}