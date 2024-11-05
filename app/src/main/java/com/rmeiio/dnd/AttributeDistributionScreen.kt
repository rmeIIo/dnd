package com.rmeiio.dnd

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun AttributeDistributionScreen(navController: NavController, characterId : Int, context: Context) {

    var forca by remember{ mutableStateOf(8) }
    var destreza by remember { mutableStateOf(8) }
    var constituicao by remember { mutableStateOf(8) }
    var inteligencia by remember { mutableStateOf(8) }
    var sabedoria by remember { mutableStateOf(8) }
    var carisma by remember { mutableStateOf(8) }
    var pontosRestantes by remember { mutableStateOf(27) }

    val databaseHelper = DatabaseHelper(context)

    LaunchedEffect(Unit) {
        val attributes = databaseHelper.getCharacterAttributes(characterId)
        if (attributes != null) {
            forca = attributes.forca ?: 8 // Use 8 como padrão se for null
            destreza = attributes.destreza ?: 8
            constituicao = attributes.constituicao ?: 8
            inteligencia = attributes.inteligencia ?: 8
            sabedoria = attributes.sabedoria ?: 8
            carisma = attributes.carisma ?: 8
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment =   Alignment.CenterHorizontally,
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
            "Distribuição de pontos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(start = 24.dp)
        )

        AttributeRow(
            attributeName = "Força",
            attributeValue = forca,
            onIncrement = {
                val costToIncrease = AttributesModifier.calculateAttributeCost(forca + 1) - AttributesModifier.calculateAttributeCost(forca)
                if (pontosRestantes >= costToIncrease && forca < 15) {
                    forca++
                    pontosRestantes -= costToIncrease
                }
            },
            onDecrement = {
                val costToDecrease = AttributesModifier.calculateAttributeCost(forca) - AttributesModifier.calculateAttributeCost(forca - 1)
                if (forca > 8) {
                    forca--
                    pontosRestantes += costToDecrease
                }
            }
        )

        AttributeRow(
            attributeName = "Destreza",
            attributeValue = destreza,
            onIncrement = {
                val costToIncrease = AttributesModifier.calculateAttributeCost(destreza + 1) - AttributesModifier.calculateAttributeCost(destreza)
                if (pontosRestantes >= costToIncrease && destreza < 15) {
                    destreza++
                    pontosRestantes -= costToIncrease
                }
            },
            onDecrement = {
                val costToDecrease = AttributesModifier.calculateAttributeCost(destreza) - AttributesModifier.calculateAttributeCost(destreza - 1)
                if (destreza > 8) {
                    destreza--
                    pontosRestantes += costToDecrease
                }
            }
        )

        AttributeRow(
            attributeName = "Constituição",
            attributeValue = constituicao,
            onIncrement = {
                val costToIncrease = AttributesModifier.calculateAttributeCost(constituicao + 1) - AttributesModifier.calculateAttributeCost(constituicao)
                if (pontosRestantes >= costToIncrease && constituicao < 15) {
                    constituicao++
                    pontosRestantes -= costToIncrease
                }
            },
            onDecrement = {
                val costToDecrease = AttributesModifier.calculateAttributeCost(constituicao) - AttributesModifier.calculateAttributeCost(constituicao - 1)
                if (constituicao > 8) {
                    constituicao--
                    pontosRestantes += costToDecrease
                }
            }
        )

        AttributeRow(
            attributeName = "Inteligência",
            attributeValue = inteligencia,
            onIncrement = {
                val costToIncrease = AttributesModifier.calculateAttributeCost(inteligencia + 1) - AttributesModifier.calculateAttributeCost(inteligencia)
                if (pontosRestantes >= costToIncrease && inteligencia < 15) {
                    inteligencia++
                    pontosRestantes -= costToIncrease
                }
            },
            onDecrement = {
                val costToDecrease = AttributesModifier.calculateAttributeCost(inteligencia) - AttributesModifier.calculateAttributeCost(inteligencia - 1)
                if (inteligencia > 8) {
                    inteligencia--
                    pontosRestantes += costToDecrease
                }
            }
        )

        AttributeRow(
            attributeName = "Sabedoria",
            attributeValue = sabedoria,
            onIncrement = {
                val costToIncrease = AttributesModifier.calculateAttributeCost(sabedoria + 1) - AttributesModifier.calculateAttributeCost(sabedoria)
                if (pontosRestantes >= costToIncrease && sabedoria < 15) {
                    sabedoria++
                    pontosRestantes -= costToIncrease
                }
            },
            onDecrement = {
                val costToDecrease = AttributesModifier.calculateAttributeCost(sabedoria) - AttributesModifier.calculateAttributeCost(sabedoria - 1)
                if (sabedoria > 8) {
                    sabedoria--
                    pontosRestantes += costToDecrease
                }
            },
        )

        AttributeRow(
            attributeName = "Carisma",
            attributeValue = carisma,
            onIncrement = {
                val costToIncrease = AttributesModifier.calculateAttributeCost(carisma + 1) - AttributesModifier.calculateAttributeCost(carisma)
                if (pontosRestantes >= costToIncrease && carisma < 15) {
                    carisma++
                    pontosRestantes -= costToIncrease
                }
            },
            onDecrement = {
                val costToDecrease = AttributesModifier.calculateAttributeCost(carisma) - AttributesModifier.calculateAttributeCost(carisma - 1)
                if (carisma > 8) {
                    carisma--
                    pontosRestantes += costToDecrease
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                databaseHelper.updateCharacterAttributes(characterId, forca, destreza, constituicao, inteligencia, sabedoria, carisma)
                navController.navigate("success")
            },
            colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                "CRIAR PERSONAGEM",
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }
}


@Composable
fun AttributeRow(
    attributeName: String,
    attributeValue: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(start = 24.dp)
            .padding(end = 24.dp)
    ) {
        Text(text = attributeName, style = MaterialTheme.typography.bodyLarge, color = Color.White)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { onDecrement() },
                enabled = attributeValue > 8
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Decrement", tint= Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = attributeValue.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .width(32.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = { onIncrement() },
                enabled = attributeValue < 15
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Increment", tint= Color.White)
            }
        }
    }
}

class AttributesModifier {
    companion object {
        fun calculateAttributeCost(attributeValue: Int): Int {
            var cost = 0

            when (attributeValue) {
                8 -> cost = 0
                9 -> cost = 1
                10 -> cost = 2
                11 -> cost = 3
                12 -> cost = 4
                13 -> cost = 5
                14 -> cost = 7
                15 -> cost = 9
            }

            return cost
        }

        fun calculateAttributeModifier(attributeValue: Int): Int {
            var modifier = 0

            when (attributeValue) {
                8, 9 -> modifier = -1
                10, 11 -> modifier = 0
                12, 13 -> modifier = 1
                14, 15 -> modifier = 2
            }

            return modifier
        }
    }
}