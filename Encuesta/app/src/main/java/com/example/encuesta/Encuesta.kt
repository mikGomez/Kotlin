package com.example.encuesta
enum class SistemaOperativo{
    LINUX,WINDOWS,MAC
}
class Encuesta {
    lateinit var nombre: String
    lateinit var so : SistemaOperativo
    var listaRespuesta: List<String>? = null
    var estudioHoras : Int = 0
    companion object {
        private var contadorEncuestas = 0
        fun NumeroEncuestas(): Int {
            return contadorEncuestas
        }
        fun numeroEncuestas(nuevoNumero: Int) {
            contadorEncuestas = nuevoNumero
        }
    }

    constructor(
        nombre: String,
        so: SistemaOperativo,
        listaRespuesta: List<String>?,
        estudioHoras: Int
    ) {
        this.nombre = nombre
        this.so = so
        this.listaRespuesta = listaRespuesta
        this.estudioHoras = estudioHoras
        contadorEncuestas++
    }
    override fun toString(): String {
        return "Encuesta(nombre=$nombre, so=$so, listaRespuesta=$listaRespuesta, estudioHoras=$estudioHoras)"
    }
}