package com.example.tresenraya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.tresenraya.databinding.ActivityMainBinding
import java.util.Arrays

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var botones : IntArray
    val tablero = Array(3) { Array(3) { 0 } }
    var cont1 : Int = 0
    var cont2 : Int = 0
    var turnoJugador = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvResultado.visibility = View.INVISIBLE

        binding.button.setOnClickListener {
            reiniciarJuego()
        }
        binding.button2.setOnClickListener {
            reiniciarPartida()
        }
        botones = IntArray(9)
        botones[0] = binding.imageButton.id
        botones[1] = binding.imageButton2.id
        botones[2] = binding.imageButton3.id
        botones[3] = binding.imageButton4.id
        botones[4] = binding.imageButton5.id
        botones[5] = binding.imageButton6.id
        botones[6] = binding.imageButton7.id
        botones[7] = binding.imageButton8.id
        botones[8] = binding.imageButton9.id

        binding.tvTodo.setBackgroundResource(R.drawable.img)
        binding.tvImagen1.setBackgroundResource(R.drawable.ralph_1)
        binding.tvImagen2.setBackgroundResource(R.drawable.simp2)

            for (i in 0 until botones.size) {
                    val botonID = botones[i]
                    val boton = findViewById<ImageButton>(botonID)
                    boton.setOnClickListener {
                        ponerImagen(boton)
                    }
            }
    }
    fun ponerImagen(boton: ImageButton) {
        val numBoton = botones.indexOf(boton.id)
        if (tablero[numBoton / 3][numBoton % 3]  != 1 && tablero[numBoton / 3][numBoton % 3]  != 2) {
            if (turnoJugador) {
                binding.tvImagen.setBackgroundResource(R.drawable.ralph_1)
                boton.setImageResource(R.drawable.xraya)
                tablero[numBoton / 3][numBoton % 3] = 1
            } else {
                binding.tvImagen.setBackgroundResource(R.drawable.simp2)
                boton.setImageResource(R.drawable.cir)
                tablero[numBoton / 3][numBoton % 3] = 2
            }
            verificarJuego()
            turnoJugador = !turnoJugador
        }
    }

    fun verificarJuego() {
        // Verificar filas
        for (i in 0 until 3) {
            if (tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2] && tablero[i][0] != 0) {
                // Un jugador ha ganado en la fila i
                mostrarResultado(tablero[i][0])
                return
            }
        }
        // Verificar columnas
        for (j in 0 until 3) {
            if (tablero[0][j] == tablero[1][j] && tablero[1][j] == tablero[2][j] && tablero[0][j] != 0) {
                mostrarResultado(tablero[0][j])
                return
            }
        }
        // Verificar diagonales
        if ((tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2] && tablero[0][0] != 0) ||
            (tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0] && tablero[0][2] != 0)) {
            mostrarResultado(tablero[1][1])
            return
        }

        // Verificar empate (si no hay más casillas vacías)
        if (tablero.flatten().none { it == 0 }) {
            mostrarResultado(0)
        }
    }

    fun mostrarResultado(ganador: Int) {
        binding.tvResultado.visibility = View.VISIBLE
        if (ganador == 1) {
            binding.tvResultado.setText("Ha ganado Jugador 1")
            cont1++;
            binding.trResult1.text = cont1.toString()
        } else if (ganador == 2) {
            binding.tvResultado.setText("Ha ganado Jugador 2")
            cont2++;
            binding.trResult2.text = cont2.toString()
        } else {
            binding.tvResultado.setText("Empate")
        }
        for (i in 0 until botones.size) {
            val boton = findViewById<ImageButton>(botones[i])
            boton.isEnabled = false
        }
    }

    fun reiniciarJuego() {
        // Limpiar el tablero
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                tablero[i][j] = 0
            }
        }
        for (i in 0 until botones.size) {
            val boton = findViewById<ImageButton>(botones[i])
            boton.isEnabled = true
            boton.setImageResource(R.drawable.toca)
        }
        turnoJugador = true
        binding.tvResultado.visibility = View.INVISIBLE
        binding.tvImagen.setBackgroundResource(R.drawable.ralph_1)
    }
    fun reiniciarPartida() {
        // Limpiar el tablero
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                tablero[i][j] = 0
            }
        }
        for (i in 0 until botones.size) {
            val boton = findViewById<ImageButton>(botones[i])
            boton.isEnabled = true
            boton.setImageResource(R.drawable.toca)
        }
        turnoJugador = true
        cont1 = 0
        cont2 = 0
        binding.trResult1.setText("0")
        binding.trResult2.setText("0")
        binding.tvResultado.visibility = View.INVISIBLE
        binding.tvImagen.setBackgroundResource(R.drawable.ralph_1)
    }

}