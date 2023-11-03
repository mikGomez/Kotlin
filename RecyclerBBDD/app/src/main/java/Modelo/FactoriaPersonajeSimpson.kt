package Modelo


object FactoriaPersonajeSimpson {
    fun generaPersonajeSimpson() : Simpson {
        var nombres = listOf<String>("Hommer", "Bart", "Snake","Apu")
        var profesion = listOf<String>("Badulaque", "Atracador", "Beber", "Gamberrear")
        var imagenes = listOf<String>("apu_prin","bart","hommer_prin","snake_prin");
        var nombrePersonaje = nombres[(nombres.indices).random()]
        var p = Simpson(nombrePersonaje, profesion[(profesion.indices).random()],imagenes[(imagenes.indices).random()])
        if (p.nombre.equals("hommer",true)) p.imagen = "hommer_prin"
        if (p.nombre.equals("bart",true)) p.imagen = "bart"
        if (p.nombre.equals("apu",true)) p.imagen = "apu_prin"
        if (p.nombre.equals("snake",true)) p.imagen = "snake_prin"
        return p
    }
}