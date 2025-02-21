package com.example.esencias

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VelasFragment : Fragment() {

    private lateinit var wishListViewModel: WishList
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptador: Adaptador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_velas, container, false)
        wishListViewModel = ViewModelProvider(requireActivity()).get(WishList::class.java)

        val db = BBDD(requireContext())
        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val listaVelas = db.listaVelas()
        val listaProductos = listaVelas.mapIndexed { index, vela ->
            Producto(
                id = index,
                nombre = vela.nombre,
                descripcion = vela.descripcion ?: "Sin descripción",
                precio = vela.precio.toDoubleOrNull() ?: 0.0,
                imagen = vela.imagen
            )
        }.toMutableList() // 🔥 Convierte la lista a MutableList



        adaptador = Adaptador(
            listaProductos,
            ::mostrarInfoVela,
            ::toggleWishList,
            ::eliminarProducto,
            ::agregarAlCarrito
        )
        recyclerView.adapter = adaptador

        return view
    }

    private fun agregarAlCarrito(producto: Producto) {
        val carritoManager = ViewModelProvider(requireActivity()).get(CarritoManager::class.java)
        Log.d("WishList", "Producto enviado al carrito: ${producto.nombre}")
        carritoManager.agregarProducto(producto)

    }


    private fun toggleWishList(producto: Producto) {
        wishListViewModel.toggleWishList(producto)
        Toast.makeText(context, "${producto.nombre} actualizado en la lista de deseos", Toast.LENGTH_SHORT).show()
    }



    private fun mostrarInfoVela(producto: Producto) {
        val fragment = InformacionVela()
        val bundle = Bundle()
        bundle.putParcelable("producto", producto)
        fragment.arguments = bundle
        fragmentLoader(fragment)
    }

    private fun eliminarProducto(producto: Producto) {
        // Implementa aquí la lógica para eliminar el producto
        Toast.makeText(context, "${producto.nombre} ha sido eliminado",  Toast.LENGTH_SHORT).show()
    }


            private fun fragmentLoader(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
