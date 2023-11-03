package com.example.recycler

import Modelo.Simpson
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recycler.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var p  = intent.getSerializableExtra("obj") as Simpson
        descripcion(p);

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
    fun descripcion(p : Simpson){
        var descripcion: String;
        if (p.nombre.equals("Apu",true)) {
            binding.txtNombreActivity2.text = "Apu Nahasapeemapetilon";
            binding.txtDescripcion.text = "Es ingeniero en informática (quien lo hubiese pensado)," +
                    " y entre sus características más notables se incluyen su exagerado acento indio, su fe hindú " +
                    "y su tenacidad en el trabajo (en los primeros capítulos de la serie establecía que el supermercado que dirige " +
                    "él solo nunca cerraba)";
            binding.ivActivity2.setImageResource(R.drawable.apu)
        }
        if (p.nombre.equals("Snake",true)){
            binding.txtNombreActivity2.text = "Chester \"Snake\" Turley";
            binding.txtDescripcion.text = "Era un arqueólogo que había encontrado monedas de oro,que pensaba donar al museo,pero al " +
                    "estar desprevenido moe se la roba,snake jura venganza y se convierte en un criminal,usualmente roba la tienda de apu y le dispara," +
                    "también con una ametralladora se subió en el tejado de apu" +
                    " y disparo diciendo: tu vives,tu mueres repetidas veces,debido a sus crímenes a estado en prisión y cuando quiere escapa";
            binding.ivActivity2.setImageResource(R.drawable.snake)
        }
        if (p.nombre.equals("hommer",true)){
            binding.txtNombreActivity2.text = "Homero Jay Simpson";
            binding.txtDescripcion.text = "Homer está desempleado y sin dinero. Para mantener a su esposa y a su hijo que venía en camino, " +
                    "consiguió un trabajo en la planta nuclear de Montgomery Burns como inspector de seguridad, gracias a la campaña de igualdad de oportunidades.\n" +
                    "Después, Marge queda embarazada de Lisa, y tiene que soportar a Bart, quién está celoso de Lisa," +
                    " la nueva integrante de la familia, Lisa sale como niña prodigio y quieren mandarla a una escuela para niños dotados.";
            binding.ivActivity2.setImageResource(R.drawable.hommer)
        }
        if (p.nombre.equals("bart",true)){
            binding.txtNombreActivity2.text = "Bartholomew \"Bart\" Jojo Simpson";
            binding.txtDescripcion.text = "Bart es decididamente el más rebelde y travieso de la familia. " +
                    "Es un muchacho simpático y también muy travieso, que hace muchas bromas con su mejor amigo Milhouse. " +
                    "Sigue los programas de su ídolo Krusty el Payaso. Es desobediente y hace todo lo que le pasa por la cabeza. Su nombre es un anagrama de \"brat\", " +
                    "que en inglés significa \"travieso\". " +
                    "Su ideología es \"yo no fui, nadie me vio, no pueden demostrarlo\".";
            binding.ivActivity2.setImageResource(R.drawable.bart_prin)
        }
    }
}