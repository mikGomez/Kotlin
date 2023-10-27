package Modelo


object FactoriaSimpson {
    fun generaPersonaje() : Simpson {
        var nombres = listOf<String>("Hommer", "Bart", "Snake","Apu")
        var cosasHacer = listOf<String>("Badulaque", "Atracador", "Beber", "Gamberrear")
        var imagenes = listOf<String>("apu","bart","hommer_prin","snake_prin");
        var nombrePersonaje = nombres[(nombres.indices).random()]
        var p = Simpson(nombrePersonaje, cosasHacer[(cosasHacer.indices).random()],imagenes[(imagenes.indices).random()])
        return p
    }
}