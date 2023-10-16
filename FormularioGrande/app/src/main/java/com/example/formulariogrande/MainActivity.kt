package com.example.formulariogrande

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.formulariogrande.databinding.ActivityMainBinding
import modelo.AlmacenPersona

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.boton.setOnClickListener{
            if (validarContenido() && validardni() && validarContra()){
                irAVentana2()
                Toast.makeText(this,resources.getString(R.string.exito) , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarContra(): Boolean {
        var valido = true
        if (binding.cnContra.text.toString() != binding.cnContra2.text.toString()){
            valido = false
            Toast.makeText(this, resources.getString(R.string.repContra), Toast.LENGTH_SHORT).show()
        }
        return valido
    }

    private fun validarContenido(): Boolean {
        var valido = true
        if (binding.cajaNombre.text.toString().isEmpty() || binding.cnEdad.text.toString().isEmpty() || binding.cnDni.text.toString().isEmpty() ||
            binding.cnGmail.text.toString().isEmpty() || binding.cnContra.text.toString().isEmpty() || binding.cnContra2.text.toString().isEmpty()){
            valido = false
            Toast.makeText(this, resources.getString(R.string.rellene), Toast.LENGTH_SHORT).show()
        }
        return valido
    }

    private fun validardni(): Boolean {
        var valido = true
        for (persona in AlmacenPersona.personas){
            if (persona.dni == binding.cnDni.text.toString()){
                valido = false
                Toast.makeText(this, resources.getString(R.string.existDni), Toast.LENGTH_SHORT).show()

            }
        }
        return valido
    }

    private fun irAVentana2() {
        var miIntent : Intent = Intent(this,Informacion::class.java)
        miIntent.putExtra("nombre",binding.cajaNombre.text.toString())
        miIntent.putExtra("edad",binding.cnEdad.text.toString())
        miIntent.putExtra("dni",binding.cnDni.text.toString())
        miIntent.putExtra("gmail",binding.cnGmail.text.toString())
        miIntent.putExtra("contrasenia",binding.cnContra.text.toString())
        startActivity(miIntent)
    }
}