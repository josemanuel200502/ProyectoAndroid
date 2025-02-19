package com.example.esencias

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VelasFragment : Fragment() {

    private val wishList = mutableListOf<Producto>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptador: Adaptador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_velas, container, false)

        val db = BBDD(requireContext())
        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Obtenemos la lista de velas desde la base de datos
        val listaVelas = db.listaVelas()

        // Convertimos la lista de velas a productos
        val listaProductos = listaVelas.mapIndexed { index, vela ->
            Producto(
                id = index, // Usamos el índice como ID ya que Vela no tiene ID
                nombre = vela.nombre,
                descripcion = vela.descripcion ?: "Sin descripción",
                precio = vela.precio.toDoubleOrNull() ?: 0.0, // Aseguramos conversión a Double
                imagen = vela.imagen
            )
        }

        adaptador = Adaptador(listaProductos, ::mostrarInfoVela, ::toggleWishList)
        recyclerView.adapter = adaptador

        return view
    }

    private fun toggleWishList(producto: Producto) {
        if (wishList.contains(producto)) {
            wishList.remove(producto)
            Toast.makeText(context, "${producto.nombre} eliminado de la lista de deseos", Toast.LENGTH_SHORT).show()
        } else {
            wishList.add(producto)
            Toast.makeText(context, "${producto.nombre} agregado a la lista de deseos", Toast.LENGTH_SHORT).show()
        }
        adaptador.notifyDataSetChanged()
    }

    private fun mostrarInfoVela(producto: Producto) {
        val fragment = InformacionVela()
        val bundle = Bundle()
        bundle.putParcelable("producto", producto)
        fragment.arguments = bundle
        fragmentLoader(fragment)
    }

    private fun fragmentLoader(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
