package com.example.esencias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
    private val listaProductos: List<Producto>,
    private val onItemClick: (Producto) -> Unit,
    private val onAgregarClick: (Producto) -> Unit
) : RecyclerView.Adapter<Adaptador.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.nombreVela)
        private val precio: TextView = itemView.findViewById(R.id.precioVela)
        private val imagen: ImageView = itemView.findViewById(R.id.imagenVela)
        private val btnAgregar: Button = itemView.findViewById(R.id.btnAgregar)

        fun bind(producto: Producto) {
            nombre.text = producto.nombre
            precio.text = "${producto.precio} €"
            Glide.with(imagen.context)
                .load(producto.imagen)
                .apply(RequestOptions().transform(RoundedCorners(40)))
                .into(imagen)

            itemView.setOnClickListener { onItemClick(producto) }
            btnAgregar.setOnClickListener { onAgregarClick(producto) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaProductos[position])
    }

    override fun getItemCount() = listaProductos.size
}
