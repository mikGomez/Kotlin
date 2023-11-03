package Modelo

object FactoriaListaSimpson {
    fun generaLista(cant:Int):ArrayList<Simpson> {
        var lista = ArrayList<Simpson>(1)
        for(i in 1..cant){
            lista.add(FactoriaPersonajeSimpson.generaPersonajeSimpson())
        }
        return lista
    }

}