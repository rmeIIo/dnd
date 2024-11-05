package com.rmeiio.dnd.services

class LadinoPontosDeVida : CalculadoraPontosDeVida {
    override fun calculaPontosDeVida(constituicao: Int): Int {
        return 8 + (constituicao + 8) / 2
    }
}