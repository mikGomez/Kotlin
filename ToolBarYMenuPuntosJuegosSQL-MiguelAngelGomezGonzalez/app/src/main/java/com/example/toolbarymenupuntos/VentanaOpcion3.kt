package com.example.toolbarymenupuntos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.toolbarymenupuntos.databinding.ActivityVentanaOpcion1Binding
import com.example.toolbarymenupuntos.databinding.ActivityVentanaOpcion3Binding
import kotlin.random.Random

class VentanaOpcion3 : AppCompatActivity() {
    lateinit var binding: ActivityVentanaOpcion3Binding
    lateinit var jugador1 : Jugador
    lateinit var jugador2 : Jugador
    var numeroAleatorio1: Int = -1
    var numeroAleatorio2: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVentanaOpcion3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreUsuario = intent.getStringExtra("usuario")
        if (nombreUsuario != null) {
            binding.nomb.text = "Usuario: $nombreUsuario"
        }

        binding.toolbarOpcion3.title = "    Mi aplicación de juegos"
        binding.toolbarOpcion3.subtitle = "     Juego Dados"
        binding.toolbarOpcion3.setLogo(R.drawable.toy)

        //aquí simplemente inflo la toolBaar, pero aún no hay opciones ni botón home.
        setSupportActionBar(binding.toolbarOpcion3)

        //en las siguientes líneas hago que aaprezca el botón de atrás (home) y genero su evento
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarOpcion3.setNavigationOnClickListener {
            Toast.makeText(this,"Pulsado el retroceso", Toast.LENGTH_SHORT).show()
            finish()
        }

        jugador1 = Jugador()
        jugador2 = Jugador()
        var valido = true
        val datoImagenes = mapOf(
            1 to R.drawable.ralph_1,
            2 to R.drawable.simp2,
            3 to R.drawable.hommer_3,
            4 to R.drawable.sim4,
            5 to R.drawable.simp5,
            6 to R.drawable.simpson6
        )


        binding.tvGanador1.visibility = View.INVISIBLE
        binding.tvGanador2.visibility = View.INVISIBLE

        binding.btnReiniciarDatos.setOnClickListener {
            reiniciarDatos(binding)
            valido = true
        }

        binding.btnreinicioJuego.setOnClickListener {
            valido = true
            binding.tvGanador1.visibility = View.INVISIBLE
            binding.tvGanador2.visibility = View.INVISIBLE
            reiniciarDatos(binding)
            jugador1.ganadas = 0
            jugador2.ganadas = 0
            binding.tvGanadas1.setText(jugador1.ganadas.toString())
            binding.tvGanadas2.setText(jugador2.ganadas.toString())
        }
        binding.btnJugador1.setOnClickListener {
            numeroAleatorio1 = jugador1.turnojugador()
            binding.tvTiradas1.setText((jugador1.tiradas - 1).toString())
            val imagen = datoImagenes[numeroAleatorio1]
            if (imagen != null) {
                binding.ivJugador1.setImageResource(imagen)
            }

            binding.tvPuntos1.setText(jugador1.puntos.toString())
            if (numeroAleatorio1 == 0) {
                Toast.makeText(this, "No se puede pinchar mas de 5 veces", Toast.LENGTH_SHORT)
                    .show()
            }
            if (jugador1.tiradas > 5 && jugador2.tiradas > 5 && valido) {
                ganador(binding)
                valido = false
            }
        }
        binding.btnJugador2.setOnClickListener {
            numeroAleatorio2 = jugador2.turnojugador()
            binding.tvTiradas2.setText((jugador2.tiradas - 1).toString())
            val imagen = datoImagenes[numeroAleatorio2]
            if (imagen != null) {
                binding.imjugador2.setImageResource(imagen)
            }
            binding.tvpuntos2.setText(jugador2.puntos.toString())
            if (numeroAleatorio2 == 0) {
                Toast.makeText(this, "No se puede pinchar mas de 5 veces", Toast.LENGTH_SHORT)
                    .show()
            }
            if (jugador1.tiradas > 5 && jugador2.tiradas > 5 && valido) {
                ganador(binding)
                valido = false
            }
        }
    }
    fun ganador(binding: ActivityVentanaOpcion3Binding) {
        if (jugador1.puntos > jugador2.puntos) {
            jugador1.ganadas += 1;
            binding.tvGanador1.visibility = View.VISIBLE
            binding.tvGanadas1.setText(jugador1.ganadas.toString())
            binding.tvGanadas2.setText(jugador2.ganadas.toString())

        }else if (jugador1.puntos < jugador2.puntos){
            jugador2.ganadas += 1;
            binding.tvGanador2.visibility = View.VISIBLE
            binding.tvGanadas1.setText(jugador1.ganadas.toString())
            binding.tvGanadas2.setText(jugador2.ganadas.toString())

        }else{
            Toast.makeText(this, "Ha sido empate", Toast.LENGTH_SHORT).show()
        }
    }
    fun reiniciarDatos(binding: ActivityVentanaOpcion3Binding){
        jugador2.resetPuntTir()
        jugador1.resetPuntTir()
        binding.tvGanador1.visibility = View.INVISIBLE
        binding.tvGanador2.visibility = View.INVISIBLE
        binding.tvPuntos1.setText("")
        binding.tvpuntos2.setText("")
        binding.tvTiradas1.setText("")
        binding.tvTiradas2.setText("")
        binding.ivJugador1.setImageResource(R.drawable.fondo)
        binding.imjugador2.setImageResource(R.drawable.fondo)
    }

    class Jugador {
        var numAleatorio = 0
        var puntos = 0
        var tiradas = 1
        var ganadas = 0

        fun turnojugador(): Int {
            if (tiradas < 6) {
                numAleatorio = numRandom()
                puntos += numAleatorio
                tiradas++
                return numAleatorio
            }
            return 0
        }

        private fun numRandom(): Int {
            return Random.nextInt(1, 6)
        }

        fun resetPuntTir() {
            puntos = 0
            tiradas = 1
        }
    }
}