package com.example.juegodados

import android.widget.Toast
import kotlin.random.Random

class Jugador {
    var numAleatorio = 0
    var puntos = 0
    var tiradas = 1
    var ganadas = 0

    fun turnojugador(): Int {
        if (tiradas < 6) {
            numAleatorio = numRandom()
            puntos += numAleatorio
            tiradas++
            return numAleatorio
        }
        return 0
    }

    private fun numRandom(): Int {
        return Random.nextInt(1, 6)
    }

    fun resetPuntTir() {
        puntos = 0
        tiradas = 1
    }
}