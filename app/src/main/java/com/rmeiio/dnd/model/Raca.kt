package com.rmeiio.dnd.model
enum class Raca(
    val bonusForca: Int,
    val bonusDestreza: Int,
    val bonusConstituicao: Int,
    val bonusInteligencia: Int,
    val bonusSabedoria: Int,
    val bonusCarisma: Int,
    val deslocamento: Int,
    val idiomas: List<String>
) {
    HUMANO(1, 1, 1, 1, 1, 1, 9, listOf("Comum")),
    ELFO(0, 2, 0, 1, 0, 0, 9, listOf("Comum", "Élfico")),
    ANÃO(0, 0, 2, 0, 0, 0, 7, listOf("Comum", "Anão")),
    HALFLING(0, 2, 0, 0, 0, 1, 7, listOf("Comum", "Halfling"))}