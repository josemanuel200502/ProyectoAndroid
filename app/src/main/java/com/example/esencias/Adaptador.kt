package com.example.esencias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/*
* Extiende a la clase Adapter de RecyclerView
* recibe la lista de velas a mostrar
* recibe tambien una funcion lambda que se ejecutará al darle click a una vela
* */

class Adaptador(
        private val listaVelas: List<Vela>,
        private val onItemClick: (Vela) -> Unit
                ): RecyclerView.Adapter<Adaptador.ViewHolder>() {

    /*
     * Clase interna ViewHolder que se encarga de gestionar las vistas individuales
     * de cada elemento del RecyclerView.
     * */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.nombreVela)
        val precio: TextView = itemView.findViewById(R.id.precioVela)
        val imagen: ImageView = itemView.findViewById(R.id.imagenVela)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cv_velas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaVelas[position]
        holder.nombre.text = item.nombre
        holder.precio.text = item.precio

        Glide.with(holder.imagen.context)
            .load(item.imagen)
            .apply(RequestOptions().transform(RoundedCorners(40)))
            .into(holder.imagen)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return listaVelas.size
    }
}