package Auxiliar

import Conexion.AdminSQLIteConexion
import Modelo.Simpson
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import com.example.sql_lite.R

object Conexion {
    private var DATABASE_NAME = "simpsons.db3"
    private var DATABASE_VERSION = 1

    fun cambiarBD(nombreBD: String) {
        this.DATABASE_NAME = nombreBD
    }

    fun addSimpson(contexto: AppCompatActivity, simpson: Simpson): Long {
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("nom", simpson.nom)
        registro.put("tipo", simpson.tipo)
        registro.put("imagen", simpson.imagen)
        val codigo = bd.insert("simpsons", null, registro)
        bd.close()
        return codigo
    }

    fun delSimpson(contexto: AppCompatActivity, nom: String): Int {
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val cant = bd.delete("simpsons", "nom=?", arrayOf(nom))
        bd.close()
        return cant
    }

    fun modSimpson(contexto: AppCompatActivity, nom: String, simpson: Simpson): Int {
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("tipo", simpson.tipo)
        registro.put("imagen", simpson.imagen)
        val cant = bd.update("simpsons", registro, "nom=?", arrayOf(nom))
        bd.close()
        return cant
    }

    fun buscarSimpson(contexto: AppCompatActivity, nom: String): Simpson? {
        var simpson: Simpson? = null
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase
        val fila = bd.rawQuery("SELECT tipo, imagen FROM simpsons WHERE nom=?", arrayOf(nom))
        if (fila.moveToFirst()) {
            simpson = Simpson(nom, fila.getString(0), fila.getString(1))
        }
        bd.close()
        return simpson
    }

    fun obtenerSimpsons(contexto: AppCompatActivity): ArrayList<Simpson> {
        val simpsons = ArrayList<Simpson>()
        val admin = AdminSQLIteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase
        val fila = bd.rawQuery("SELECT nom, tipo, imagen FROM simpsons", null)
        while (fila.moveToNext()) {
            val simpson = Simpson(fila.getString(0), fila.getString(1), fila.getString(2))
            simpsons.add(simpson)
        }
        bd.close()
        return simpsons
    }
}

