package Modelo


object FactoriaSimpson {
    fun generaPersonaje() : Simpson {
        var nombres = listOf<String>("Hommer", "Bart", "Snake", "Apu")
        var cosasHacer = listOf<String>("Badulaque", "Atracador", "Beber", "Gamberrear")
        var imagenes = listOf<String>("https://loremflickr.com/g/320/240/paris","https://loremflickr.com/320/240/dog","https://loremflickr.com/320/240","https://loremflickr.com/320/240/brazil,rio", "https://loremflickr.com/320/240/paris,girl/all");
        var nombrePersonaje = nombres[(nombres.indices).random()]
        var p = Simpson(nombrePersonaje, cosasHacer[(cosasHacer.indices).random()],imagenes[(imagenes.indices).random()])
        if (p.nombre.equals("Gandalf")) p.imagen="gandalf"

        return p
    }
}