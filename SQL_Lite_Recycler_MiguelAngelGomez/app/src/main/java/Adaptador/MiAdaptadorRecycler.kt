package Adaptador

import Modelo.Simpson
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sql_lite.R

class MiAdaptadorRecycler(private var listaSimpsons: List<Simpson>) :
    RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomTextView: TextView = itemView.findViewById(R.id.txtNombre)
        val tipoTextView: TextView = itemView.findViewById(R.id.txtRaza)
        val imagenTextView: TextView = itemView.findViewById(R.id.txtNombre3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_persona, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val simpson = listaSimpsons[position]
        holder.nomTextView.text = simpson.nom
        holder.tipoTextView.text = simpson.tipo
        val imagenSimpson = holder.itemView.findViewById<ImageView>(R.id.imgImagen)
        val imagenResource = holder.itemView.resources.getIdentifier(simpson.imagen, "drawable", holder.itemView.context.packageName)
        imagenSimpson.setImageResource(imagenResource)
    }

    override fun getItemCount(): Int {
        return listaSimpsons.size
    }

    fun setSimpsons(nuevaListaSimpsons: List<Simpson>) {
        listaSimpsons = nuevaListaSimpsons
        notifyDataSetChanged()
    }
}

