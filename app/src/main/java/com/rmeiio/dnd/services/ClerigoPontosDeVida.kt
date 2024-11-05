package com.rmeiio.dnd.services

class ClerigoPontosDeVida : CalculadoraPontosDeVida {
    override fun calculaPontosDeVida(constituicao: Int): Int {
        return 8 + (constituicao + 8) / 2
    }
}