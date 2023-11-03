package Modelo

object Almacen {
    lateinit var simpsons : ArrayList<Simpson>
    fun agregarSimpson(nuevoSimpson: Simpson) {
        simpsons.add(nuevoSimpson)
    }

}