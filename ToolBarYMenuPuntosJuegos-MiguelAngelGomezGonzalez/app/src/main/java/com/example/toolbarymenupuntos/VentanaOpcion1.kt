package com.example.toolbarymenupuntos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.Toast
import com.example.toolbarymenupuntos.databinding.ActivityMainBinding
import com.example.toolbarymenupuntos.databinding.ActivityVentanaOpcion1Binding
import kotlin.random.Random

class VentanaOpcion1 : AppCompatActivity() {
    lateinit var binding: ActivityVentanaOpcion1Binding
    lateinit var botones: Array<ImageButton>
    var contador: Int = 1
    var record = 0
    var detenerJuego = false
    val imagenPorNumero = mapOf(
        0 to R.drawable.caplow,
        1 to R.drawable.ironlow,
        2 to R.drawable.punilow,
        3 to R.drawable.deadlow
    )
    val imagenPorNumeroCambiar = mapOf(
        0 to R.drawable.capbri,
        1 to R.drawable.ironbri,
        2 to R.drawable.punibri,
        3 to R.drawable.deadbri
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_ventana_opcion1)
        binding = ActivityVentanaOpcion1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarOpcion1.title = "    Mi aplicación de juegos"
        binding.toolbarOpcion1.subtitle = "     Juego Simon"
        binding.toolbarOpcion1.setLogo(R.drawable.toy)

        val nombreUsuario = intent.getStringExtra("usuario")
        if (nombreUsuario != null) {
            binding.nom.text = "Usuario: $nombreUsuario"
        }

        //aquí simplemente inflo la toolBaar, pero aún no hay opciones ni botón home.
        setSupportActionBar(binding.toolbarOpcion1)

        //en las siguientes líneas hago que aaprezca el botón de atrás (home) y genero su evento
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarOpcion1.setNavigationOnClickListener {
            Toast.makeText(this,"Pulsado el retroceso", Toast.LENGTH_SHORT).show()
            finish()
        }
        botones = arrayOf(
            binding.ibCapA6,
            binding.ibIron,
            binding.ibHulk,
            binding.ibthor
        )

        var listaCreada = ArrayList<Int>()
        var listaUsuario = ArrayList<Int>()
        binding.textView.setText("Record: $record")
        binding.textView2.setText("Ronda: $contador")


        binding.btnReinicio.setOnClickListener {
            binding.btnSecuencia.isEnabled = true
            listaCreada.clear()
            listaUsuario.clear()
            contador=1
            binding.textView2.setText("Ronda: $contador")
            detenerJuego = true
        }
        binding.btnSecuencia.setOnClickListener {
            detenerJuego = false
            binding.btnSecuencia.isEnabled = false
            binding.ibIron.isClickable = false
            binding.ibCapA6.isClickable = false
            binding.ibHulk.isClickable = false
            binding.ibthor.isClickable = false
            listaCreada = hacerSecuencia(contador)
        }

        binding.ibCapA6.setOnClickListener {
            listaUsuario.add(0)
            if (listaUsuario.size == contador){
                if(verificarSecuencia(listaUsuario,listaCreada)){
                    if (contador > record){
                        record = contador
                        binding.textView.setText("Record: $record")
                    }
                    contador++
                }else{
                    contador = 1
                }
                binding.textView2.setText("Ronda: $contador")
                listaCreada.clear()
                listaUsuario.clear()
            }
        }

        binding.ibIron.setOnClickListener {
            listaUsuario.add(1)
            if (listaUsuario.size == contador){
                if(verificarSecuencia(listaUsuario,listaCreada)){
                    if (contador > record){
                        record = contador
                        binding.textView.setText("Record: $record")
                    }
                    contador++
                }else{
                    contador = 1
                }
                binding.textView2.setText("Ronda: $contador")
                listaCreada.clear()
                listaUsuario.clear()
            }
        }

        binding.ibHulk.setOnClickListener {
            listaUsuario.add(2)
            if (listaUsuario.size == contador){
                if(verificarSecuencia(listaUsuario,listaCreada)){
                    if (contador > record){
                        record = contador
                        binding.textView.setText("Record: $record")
                    }
                    contador++
                }else{
                    contador = 1
                }
                binding.textView2.setText("Ronda: $contador")
                listaCreada.clear()
                listaUsuario.clear()
            }
        }

        binding.ibthor.setOnClickListener {
            listaUsuario.add(3)
            if (listaUsuario.size == contador){
                if(verificarSecuencia(listaUsuario,listaCreada)){
                    if (contador > record){
                        record = contador
                        binding.textView.setText("Record: $record")
                    }
                    contador++
                }else{
                    contador = 1
                }
                binding.textView2.setText("Ronda: $contador")
                listaCreada.clear()
                listaUsuario.clear()
            }
        }
    }
    fun hacerSecuencia(nivel: Int): ArrayList<Int> {
        var i = 0
        val listaCreada = ArrayList<Int>()
        var ultimoNumeroRandom: Int? = null

        fun mostrarSiguienteImagen() {
            if (i < nivel && !detenerJuego) {
                Handler().postDelayed({
                    var numeroRandom = Random.nextInt(0, 4)
                    ultimoNumeroRandom = numeroRandom

                    val boton = botones[numeroRandom]

                    boton.setImageResource(imagenPorNumeroCambiar[numeroRandom] ?: R.drawable.deadbri)
                    listaCreada.add(numeroRandom)
                    Handler().postDelayed({
                        boton.setImageResource(imagenPorNumero[numeroRandom] ?: R.drawable.deadbri)
                        i++
                        mostrarSiguienteImagen()
                    }, 1000) // Cambia la duración de acuerdo a tus necesidades
                }, 1000)

            } else {
                binding.ibIron.isClickable = true
                binding.ibCapA6.isClickable = true
                binding.ibHulk.isClickable = true
                binding.ibthor.isClickable = true
                if (!detenerJuego) {
                    Toast.makeText(this, "Es tu hora", Toast.LENGTH_SHORT).show()
                }
            }
        }
        mostrarSiguienteImagen()
        return listaCreada
    }

    fun verificarSecuencia(secuenciaJugador: ArrayList<Int>, secuenciaCreada: ArrayList<Int>) : Boolean {
        // Aquí puedes comparar la secuencia del jugador con la generada por la computadora
        // y tomar medidas según el resultado.
        var iguales =false
        if (secuenciaJugador.size == secuenciaCreada.size) {
            // Ambas secuencias tienen la misma longitud, verifica si son iguales
            if (secuenciaJugador.equals(secuenciaCreada)) {
                Toast.makeText(this, "Has superado el nivel", Toast.LENGTH_SHORT).show()
                iguales = true
            } else {
                // La secuencia del jugador es incorrecta
                // Realiza acciones correspondientes
                Toast.makeText(this, "Secuencia incorrecta, Vuelta a empezar", Toast.LENGTH_SHORT).show()
            }
            binding.btnSecuencia.isEnabled = true
        }else{
            Toast.makeText(this, "Espere su turno, tiene que volver a empezar", Toast.LENGTH_SHORT).show()
        }
        return iguales
    }

}