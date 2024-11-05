package com.rmeiio.dnd.model
import com.rmeiio.dnd.services.CalculadoraPontosDeVida

data class Personagem(
    val nome: String,
    var forca: Int,
    var destreza: Int,
    var constituicao: Int,
    var inteligencia: Int,
    var sabedoria: Int,
    var carisma: Int,
    var pontosDeVida: Int,
    var deslocamento: Int,
    var idiomas: List<String>,
    var classe: Classe,
    var bonusProficiencia: Int,
    private var calculadoraPontosDeVida: CalculadoraPontosDeVida
) {
    fun aplicarBonusRacial(raca: Raca) {
        forca += raca.bonusForca
        destreza += raca.bonusDestreza
        constituicao += raca.bonusConstituicao
        inteligencia += raca.bonusInteligencia
        sabedoria += raca.bonusSabedoria
        carisma += raca.bonusCarisma
        deslocamento = raca.deslocamento
        idiomas = raca.idiomas
        pontosDeVida = calculadoraPontosDeVida.calculaPontosDeVida(constituicao)
    }

    fun exibirInfo() {
        println("Nome: $nome")
        println("Classes: $classe")
        println("Força: $forca")
        println("Destreza: $destreza")
        println("Constituição: $constituicao")
        println("Inteligência: $inteligencia")
        println("Sabedoria: $sabedoria")
        println("Carisma: $carisma")
        println("Pontos de Vida: $pontosDeVida")
        println("Deslocamento: $deslocamento metros")
        println("Idiomas: ${idiomas.joinToString(", ")}")
        println("Bônus de Proficiência: +$bonusProficiencia")
    }
}