package com.example.encuesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Toast
import com.example.encuesta.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var encuestas = mutableListOf<Encuesta>()
    var progreso :Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.tvHoras.setText("Cambiando progreso")
                progreso = (binding.seekBar.progress /10.0).toInt()
                binding.tvHoras.setText(progreso.toString())
            }
            override fun onStartTrackingTouch(seek: SeekBar?) {
                binding.tvHoras.setText("Starting tracking")
            }

            override fun onStopTrackingTouch(seek: SeekBar?) {
                binding.tvHoras.setText("Stopping tracking")
                progreso = (binding.seekBar.progress /10.0).toInt()
                binding.tvHoras.setText(progreso.toString())
            }
    })
        binding.btnValidar.setOnClickListener {
           var nombre : String = nombre()
            var so : SistemaOperativo = nomSistema()
            var listaEspe : MutableList<String>? = listaEspecialidad()
            if (nombre.isNotEmpty()){
                var encuesta = Encuesta(nombre,so,listaEspe,progreso)
                encuestas.add(encuesta)
                reinicio()
                Toast.makeText(this, "Creado con exito", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnResumen.setOnClickListener {
            var texto = StringBuilder()
            for (enc in encuestas){
                texto.append(enc.toString())
                texto.append("\n")
            }
            binding.tvLista.setText(texto.toString())
        }
        binding.btnCuantas.setOnClickListener {
            Toast.makeText(this, "Hay: ${Encuesta.NumeroEncuestas()} Encuestas", Toast.LENGTH_SHORT).show()
        }
        binding.btnReiniciar.setOnClickListener {
            encuestas.clear()
            Encuesta.numeroEncuestas(0)
            reinicio()
        }

}

    fun nombre() : String {
        var nombre: String? = null
        if (binding.switch1.isChecked) {
            nombre = "Anónimo"
        } else {
            nombre = binding.etNom.text.toString()
            if (nombre.isNotEmpty()) {
                return nombre
                binding.switch1.isChecked = false
            } else {
                Toast.makeText(this, "Por favor, ingrese un nombre", Toast.LENGTH_SHORT).show()
            }
        }
        return nombre
    }
    fun nomSistema () : SistemaOperativo{
        val rbSeleccionado = findViewById<RadioButton>(binding.radioGroup.checkedRadioButtonId)
        return when (rbSeleccionado) {
            binding.radioButton -> SistemaOperativo.LINUX
            binding.radioButton2 -> SistemaOperativo.MAC
            binding.radioButton3 -> SistemaOperativo.WINDOWS
            else -> SistemaOperativo.WINDOWS
        }
    }
    fun listaEspecialidad(): MutableList<String>? {
        var listEsp: MutableList<String> = mutableListOf()
        if (binding.cbDAW.isChecked){
            listEsp.add("DAW")
            }
        if (binding.cbDAM.isChecked){
            listEsp.add("DAM")
        }
        if (binding.cbASIR.isChecked){
            listEsp.add("ASIR")
        }
        return listEsp
    }
    fun reinicio(){
        binding.etNom.setText("")
        binding.cbDAM.isChecked = false
        binding.cbDAW.isChecked = false
        binding.cbASIR.isChecked = false
        binding.seekBar.progress = 0
    }
}
