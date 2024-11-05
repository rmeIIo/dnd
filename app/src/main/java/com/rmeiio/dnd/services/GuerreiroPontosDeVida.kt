package com.rmeiio.dnd.services

class GuerreiroPontosDeVida : CalculadoraPontosDeVida {
    override fun calculaPontosDeVida(constituicao: Int): Int {
        return 10 + (constituicao + 10) / 2
    }
}