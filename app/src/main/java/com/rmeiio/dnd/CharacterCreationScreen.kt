package com.rmeiio.dnd

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rmeiio.dnd.model.Classe
import com.rmeiio.dnd.model.Raca

@Composable
fun CharacterCreationScreen() {
    var nome by remember { mutableStateOf("") }
    var forca by remember { mutableStateOf(8) }
    var destreza by remember { mutableStateOf(8) }
    var constituicao by remember { mutableStateOf(8) }
    var inteligencia by remember { mutableStateOf(8) }
    var sabedoria by remember { mutableStateOf(8) }
    var carisma by remember { mutableStateOf(8) }
    var pontosRestantes by remember { mutableStateOf(27) }
    var raca by remember { mutableStateOf(Raca.HUMANO.name) }
    var classe by remember { mutableStateOf(Classe.GUERREIRO.name) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Criação de Personagem", style = MaterialTheme.typography.h4)

        // Campo para Nome do Personagem
        TextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") })

        // Campos para Atributos
        AtributoInput("Força", forca) { valor ->
            if (pontosRestantes >= valor - forca && valor in 8..15) {
                pontosRestantes += forca - valor
                forca = valor
            }
        }

        AtributoInput("Destreza", destreza) { valor ->
            if (pontosRestantes >= valor - destreza && valor in 8..15) {
                pontosRestantes += destreza - valor
                destreza = valor
            }
        }

        AtributoInput("Constituição", constituicao) { valor ->
            if (pontosRestantes >= valor - constituicao && valor in 8..15) {
                pontosRestantes += constituicao - valor
                constituicao = valor
            }
        }

        AtributoInput("Inteligência", inteligencia) { valor ->
            if (pontosRestantes >= valor - inteligencia && valor in 8..15) {
                pontosRestantes += inteligencia - valor
                inteligencia = valor
            }
        }

        AtributoInput("Sabedoria", sabedoria) { valor ->
            if (pontosRestantes >= valor - sabedoria && valor in 8..15) {
                pontosRestantes += sabedoria - valor
                sabedoria = valor
            }
        }

        AtributoInput("Carisma", carisma) { valor ->
            if (pontosRestantes >= valor - carisma && valor in 8..15) {
                pontosRestantes += carisma - valor
                carisma = valor
            }
        }

        Text("Pontos restantes: $pontosRestantes")

        // Seleção de Raça e Classe
        DropdownMenuInput("Raça", Raca.values().map { it.name }, raca) { racaSelecionada ->
            raca = racaSelecionada
        }

        DropdownMenuInput("Classe", Classe.values().map { it.name }, classe) { classeSelecionada ->
            classe = classeSelecionada
        }

        // Botão para Criar Personagem
        Button(onClick = {
            val personagem = criarPersonagem(nome, forca, destreza, constituicao, inteligencia, sabedoria, carisma, raca, classe)
            personagem.exibirInfo() // Exibe as informações do personagem ou armazena em algum lugar.
        }) {
            Text("Criar Personagem")
        }
    }
}

// Função auxiliar para o input dos atributos.
@Composable
fun AtributoInput(label: String, value: Int, onValueChange: (Int) -> Unit) {
    var tempValue by remember { mutableStateOf(value.toString()) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label)
        Spacer(modifier = Modifier.width(8.dp))
        TextField(
            value = tempValue,
            onValueChange = {
                tempValue = it.filter { char -> char.isDigit() } // Filtra apenas números.
                onValueChange(tempValue.toIntOrNull() ?: value)
            },
            modifier = Modifier.width(60.dp)
        )
    }
}

// Função auxiliar para Dropdown Menu.
@Composable
fun DropdownMenuInput(label: String, items: List<String>, selectedItem: String, onItemSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = selectedItem,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    expanded = false
                }) {
                    Text(item)
                }
            }
        }
    }
}