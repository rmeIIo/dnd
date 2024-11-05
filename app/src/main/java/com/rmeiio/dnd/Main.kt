package com.rmeiio.dnd
import com.rmeiio.dnd.model.Raca
import com.rmeiio.dnd.model.Personagem
import com.rmeiio.dnd.utils.Leitor
import com.rmeiio.dnd.model.Classe
import com.rmeiio.dnd.services.CalculadoraPontosDeVida
import com.rmeiio.dnd.services.GuerreiroPontosDeVida
import com.rmeiio.dnd.services.LadinoPontosDeVida
import com.rmeiio.dnd.services.MagoPontosDeVida
import com.rmeiio.dnd.services.ClerigoPontosDeVida

fun criarPersonagem(nome: String, forca: Int, destreza: Int, constituicao: Int,
                    inteligencia: Int, sabedoria: Int, carisma: Int,
                    racaString: String, classeString: String): Personagem {

    val racaEnum = Raca.valueOf(racaString.uppercase())
    val classeEnum = Classe.valueOf(classeString.uppercase())

    val calculadoraPontosDeVida: CalculadoraPontosDeVida = when (classeEnum) {
        Classe.GUERREIRO -> GuerreiroPontosDeVida()
        Classe.LADINO -> LadinoPontosDeVida()
        Classe.MAGO -> MagoPontosDeVida()
        Classe.CLERIGO -> ClerigoPontosDeVida()
    }

    val personagem = Personagem(
        nome,
        forca,
        destreza,
        constituicao,
        inteligencia,
        sabedoria,
        carisma,
        calculadoraPontosDeVida.calculaPontosDeVida(constituicao),
        deslocamento = 0,
        idiomas = emptyList(),
        classe = classeEnum,
        bonusProficiencia = 2,
        calculadoraPontosDeVida
    )

    personagem.aplicarBonusRacial(racaEnum)

    return personagem // Retorna o personagem criado.
}

fun distribuirPontosAtributos(): List<Int> {
    val atributos = mutableListOf(8, 8, 8, 8, 8, 8) // Inicializa todos os atributos com 8
    val nomesAtributos = listOf("Força", "Destreza", "Constituição", "Inteligência", "Sabedoria", "Carisma")
    var pontosRestantes = 27

    fun obterValorAtributo(nomeAtributo: String): Int {
        var valor: Int
        do {
            print("Digite o valor para $nomeAtributo (entre 8 e 15): ")
            valor = readLine()?.toIntOrNull() ?: 8 // Valor padrão se entrada inválida
            if (valor !in 8..15) {
                println("Valor inválido. Insira um valor entre 8 e 15.")
            }
        } while (valor !in 8..15)
        return valor
    }

    while (pontosRestantes > 0) {
        println("\nPontos restantes: $pontosRestantes")
        println("Distribuição atual dos atributos:")
        for (i in atributos.indices) {
            println("${nomesAtributos[i]}: ${atributos[i]}")
        }

        val atributoSelecionado = Leitor.lerInt("Selecione o atributo que deseja melhorar (1-Força, 2-Destreza, 3-Constituição, 4-Inteligência, 5-Sabedoria, 6-Carisma): ")

        if (atributoSelecionado in 1..6) {
            val index = atributoSelecionado - 1
            println("${nomesAtributos[index]}: ${atributos[index]} (atual)")

            val incremento = obterValorAtributo(nomesAtributos[index])

            if (incremento + atributos[index] <= 15 && (incremento - atributos[index]) <= pontosRestantes) {
                pontosRestantes -= (incremento - atributos[index])
                atributos[index] = incremento
                println("${nomesAtributos[index]} agora tem ${atributos[index]} pontos.")
            } else {
                println("Valor inválido. Certifique-se de que os pontos não ultrapassem o limite de 15 e que você tenha pontos suficientes.")
            }
        } else {
            println("Seleção inválida. Escolha um número entre 1 e 6.")
        }
    }

    println("\nDistribuição final dos atributos:")
    for (i in atributos.indices) {
        println("${nomesAtributos[i]}: ${atributos[i]}")
    }

    return atributos
}
