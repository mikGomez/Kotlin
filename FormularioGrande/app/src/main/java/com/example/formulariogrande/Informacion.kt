package com.example.formulariogrande

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formulariogrande.databinding.ActivityInformacion2Binding
import modelo.AlmacenPersona
import modelo.Persona

class Informacion : AppCompatActivity() {
    lateinit var binding: ActivityInformacion2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformacion2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var nombre = intent.getStringExtra("nombre")
        var edad = intent.getStringExtra("edad")
        var dni = intent.getStringExtra("dni")
        var gmail = intent.getStringExtra("gmail")
        var contra = intent.getStringExtra("contra")
        var persona: Persona = Persona(nombre,edad,dni,gmail,contra)



        binding.cajaNombre.setText(nombre)
        binding.cajaEdad.setText(edad)
        binding.cnDNI.setText(dni)

        AlmacenPersona.aniadirPersona(persona)
        var cadena: String = ""
        var i:Int = 1
        for (p in AlmacenPersona.personas){
            cadena += " " +i+"," +p.nombre +" "+p.edad + " "+ p.dni + " " + p.gmail + "\n"
            i++
            binding.multiLine.setText(cadena)
        }
        binding.boton.setOnClickListener{
            finish()
        }
    }
}