package com.example.sql_lite

import Adaptador.MiAdaptadorRecycler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import Auxiliar.Conexion
import Modelo.Simpson
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {
    lateinit var edNombre: EditText
    lateinit var edTipo: EditText
    lateinit var edImagen: EditText
    lateinit var botonAdd: Button
    lateinit var botonBuscar: Button
    lateinit var botonBorrar: Button
    lateinit var botonEditar: Button
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MiAdaptadorRecycler
    var imagenes = listOf<String>("apu","bart_prin","hommer_prin","snake");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ACSCO", "Paso por OnCreate")
        setContentView(R.layout.activity_main)
        edNombre = findViewById(R.id.edNombre)
        edNombre.requestFocus()
        edTipo = findViewById(R.id.edTipo)
        edImagen = findViewById(R.id.edImagen)
        botonAdd = findViewById(R.id.btnAdd)
        botonBuscar = findViewById(R.id.btnBuscar)
        botonBorrar = findViewById(R.id.btnBorrar)
        botonEditar = findViewById(R.id.btnEditar)
        recyclerView = findViewById(R.id.listado)

        Log.e("ACSCO", "Initializing adapter and RecyclerView")
        adapter = MiAdaptadorRecycler(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        Log.e("ACSCO", "Paso por todo")
    }

    fun addSimpson(view: View) {
        if (edNombre.text.toString().trim().isEmpty() || edTipo.text.toString().trim().isEmpty()
            || edImagen.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(this, "Campos en blanco", Toast.LENGTH_SHORT).show()
        } else {
            val simpson = Simpson(
                edNombre.text.toString(),
                edTipo.text.toString(),
                imagenes[(imagenes.indices).random()]
            )
            val codigo = Conexion.addSimpson(this, simpson)
            edNombre.setText("")
            edTipo.setText("")
            edImagen.setText("")
            edNombre.requestFocus()

            if (codigo != -1L) {
                Toast.makeText(this, "Simpson insertado", Toast.LENGTH_SHORT).show()
                listarSimpsons(view)
            } else {
                Toast.makeText(this, "Simpson ya existe", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun delSimpson(view: View) {
        val cant = Conexion.delSimpson(this, edNombre.text.toString())
        edNombre.setText("")
        edTipo.setText("")
        edImagen.setText("")

        if (cant == 1) {
            Toast.makeText(this, "Simpson borrado", Toast.LENGTH_SHORT).show()
            listarSimpsons(view)
        } else {
            Toast.makeText(this, "No existe ese Simpson", Toast.LENGTH_SHORT).show()
        }
    }

    fun modSimpson(view: View) {
        if (edNombre.text.toString().trim().isEmpty() || edTipo.text.toString().trim().isEmpty()
            || edImagen.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(this, "Campos en blanco", Toast.LENGTH_SHORT).show()
        } else {
            val simpson = Simpson(
                edNombre.text.toString(),
                edTipo.text.toString(),
                imagenes[(imagenes.indices).random()]
            )
            val cant = Conexion.modSimpson(this, edNombre.text.toString(), simpson)
            if (cant == 1) {
                Toast.makeText(this, "Simpson modificado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No existe ese simpson", Toast.LENGTH_SHORT).show()
            }
        }
        listarSimpsons(view)
    }

    fun buscarSimpson(view: View) {
        val simpson = Conexion.buscarSimpson(this, edNombre.text.toString())
        if (simpson != null) {
            edTipo.setText(simpson.tipo)
            edImagen.setText(simpson.imagen)
        } else {
            Toast.makeText(this, "No existe ese simpson", Toast.LENGTH_SHORT).show()
        }
    }

    fun listarSimpsons(view: View) {
        val listaSimpsons = Conexion.obtenerSimpsons(this)
        adapter.setSimpsons(listaSimpsons)
    }
}


