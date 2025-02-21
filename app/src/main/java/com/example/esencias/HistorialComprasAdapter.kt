package com.example.esencias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistorialComprasAdapter(
    private val listaCompras: List<Compra>
) : RecyclerView.Adapter<HistorialComprasAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fechaCompra: TextView = itemView.findViewById(R.id.fecha_compra)
        val productosCompra: TextView = itemView.findViewById(R.id.productos_compra)

        fun bind(compra: Compra) {
            val fecha = java.text.DateFormat.getDateTimeInstance().format(compra.fechaCompra)
            fechaCompra.text = fecha
            productosCompra.text = compra.listaProductos.joinToString(separator = "\n") { it.nombre }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_compra, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaCompras[position])
    }

    override fun getItemCount() = listaCompras.size
}
