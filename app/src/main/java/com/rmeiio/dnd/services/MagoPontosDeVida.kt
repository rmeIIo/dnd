package com.rmeiio.dnd.services

class MagoPontosDeVida : CalculadoraPontosDeVida {
    override fun calculaPontosDeVida(constituicao: Int): Int {
        return 6 + (constituicao + 6) / 2
    }
}