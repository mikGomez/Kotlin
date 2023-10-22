package com.example.juegodados

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.juegodados.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var jugador1 : Jugador
    lateinit var jugador2 : Jugador
    var numeroAleatorio1: Int = -1
    var numeroAleatorio2: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //infla la ventana del layout del que estamos
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

    fun ganador(binding: ActivityMainBinding) {
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
    fun reiniciarDatos(binding: ActivityMainBinding){
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
}
