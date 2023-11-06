package Auxiliar

import Conexion.AdminSQLIteConexion
import Modelo.Persona
import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

object Conexion {
    //Clase tipo Singleton para acceder e métodos sin tener que crear objeto (similar a Static de Java)
    //Si hay algún cambio en la BBDD, se cambia el número de versión y así automáticamente
    // se pasa por el OnUpgrade del AdminSQLite
    //y ahí añades las sentencias que interese modificar de la BBDD
    private  var DATABASE_NAME = "administracion.db3"
    private  var DATABASE_VERSION = 1


    fun cambiarBD(nombreBD:String){
        this.DATABASE_NAME = nombreBD
    }

    fun addPersona(contexto: AppCompatActivity, p: Persona):Long{
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase //habilito la BBDD para escribir en ella, tambié deja leer.
        val registro = ContentValues() //objeto de kotlin, contenido de valores, un Map. Si haceis ctrl+clic lo veis.
        registro.put("nombre",p.nombre)
        registro.put("record", p.record.toString())
        val codigo = bd.insert("personas", null, registro)
        bd.close()
        return codigo
    }

    fun delPersona(contexto: AppCompatActivity, nombre: String):Int{
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        //val cant = bd.delete("personas", "dni='${dni}'", null)
        val cant = bd.delete("personas", "nombre=?", arrayOf(nombre.toString()))
        bd.close()
        return cant
    }

    fun modPersona(contexto: AppCompatActivity, nombre: String, p: Persona): Int {
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("nombre", p.nombre)
        registro.put("record", p.record)

        // Utiliza el nombre proporcionado como argumento para la cláusula WHERE
        val cant = bd.update("personas", registro, "nombre=?", arrayOf(nombre))

        bd.close()
        return cant
    }

    fun buscarPersona(contexto: AppCompatActivity, nombre:String):Persona? {
        var p:Persona? = null //si no encuentra ninguno vendrá null, por eso la ? y también en la devolución de la función.
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase
        /*Esta funciona pero es mejor como está hecho justo debajo con ?
        val fila = bd.rawQuery(
            "select nombre,edad from personas where dni='${dni}'",
            null
        )*/
        val fila =bd.rawQuery(
            "SELECT record FROM personas WHERE nombre=?",
            arrayOf(nombre.toString())
        )
        //en fila viene un CURSOR, que está justo antes del primero por eso lo ponemos en el primero en la siguiente línea
        if (fila.moveToFirst()) {//si no hay datos el moveToFirst, devuelve false, si hay devuelve true.
            p = Persona(nombre, fila.getInt(0))
        }
        bd.close()
        return p
    }

    fun obtenerPersonas(contexto: AppCompatActivity):ArrayList<Persona>{
        var personas:ArrayList<Persona> = ArrayList(1)

        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase
        val fila = bd.rawQuery("select nombre,record from personas", null)
        while (fila.moveToNext()) {
            var p:Persona = Persona(fila.getString(0),fila.getInt(1))
            personas.add(p)
        }
        bd.close()
        return personas //este arrayList lo puedo poner en un adapter de un RecyclerView por ejemplo.
    }
}