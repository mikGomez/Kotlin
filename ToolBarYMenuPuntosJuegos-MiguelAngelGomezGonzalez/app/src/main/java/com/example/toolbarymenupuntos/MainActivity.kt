package com.example.toolbarymenupuntos

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.toolbarymenupuntos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var usuario :String
    var isMenuActive = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarPrincipal.title = "    Mi aplicación de juegos"
        binding.toolbarPrincipal.subtitle = "     Menu Principal"
        binding.toolbarPrincipal.setLogo(R.drawable.toy)

        binding.button.setOnClickListener {
            usuario = binding.etNom.text.toString()

            if (usuario.isEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Nombre")
                builder.setMessage("Por favor, ingresa un nombre")
                builder.setPositiveButton("Aceptar", null)
                builder.show()

                // Si se ingresa un nombre vacío, desactiva el menú y las actividades.
                isMenuActive = false
                invalidateOptionsMenu()
            } else {
                isMenuActive = true
                setSupportActionBar(binding.toolbarPrincipal)
                invalidateOptionsMenu()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Nombre")
                builder.setMessage("Nombre ingresado correctamente: $usuario")
                builder.setPositiveButton("Aceptar", null)
                builder.show()
                binding.etNom.text.clear()
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarPrincipal.setNavigationOnClickListener {
            Toast.makeText(this, "Pulsado el retroceso", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isMenuActive) {
            menuInflater.inflate(R.menu.menu, menu)
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnOp1 -> {
                irAVentanaOpcion1()
                Toast.makeText(this, "Opción 1", Toast.LENGTH_LONG).show()
            }
            R.id.mnOp2 -> {
                Toast.makeText(this, "Opción 2", Toast.LENGTH_SHORT).show()
            }
            R.id.mnOp3 -> {
                irAVentanaOpcion3()
                Toast.makeText(this, "Opción 3", Toast.LENGTH_SHORT).show()
            }
            R.id.mnBusqueda -> {
                Toast.makeText(this, "Buscar", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private val irAVentanaOpcion1: () -> Unit = {
        val miIntent = Intent(this, VentanaOpcion1::class.java)
        miIntent.putExtra("usuario", usuario)
        startActivity(miIntent)
    }

    private val irAVentanaOpcion3: () -> Unit = {
        val miIntent = Intent(this, VentanaOpcion3::class.java)
        miIntent.putExtra("usuario", usuario)
        startActivity(miIntent)
    }
}