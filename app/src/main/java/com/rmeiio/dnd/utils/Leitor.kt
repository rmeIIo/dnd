package com.rmeiio.dnd.utils
import java.util.*

object Leitor {
    private val scanner = Scanner(System.`in`)

    fun lerString(mensagem: String): String {
        print(mensagem)
        return scanner.nextLine()
    }

    fun lerInt(mensagem: String): Int {
        print(mensagem)
        return scanner.nextLine().toIntOrNull() ?: 0
    }

    fun lerRaca(): String {
        println("Escolha a raça do personagem: HUMANO, ELFO, ANAO, HALFLING, ORC")
        return lerString("Digite a raça: ")
    }

    fun lerClasse(): String {
        println("Escolha a classe do personagem: GUERREIRO, LADINO, MAGO, CLERIGO")
        return lerString("Digite a classe: ")
    }
}