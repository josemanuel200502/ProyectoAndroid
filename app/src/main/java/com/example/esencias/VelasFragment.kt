package com.example.esencias

import android.os.Bundle
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
        }

        adaptador = Adaptador(listaProductos, ::mostrarInfoVela, ::toggleWishList)
        recyclerView.adapter = adaptador

        return view
    }

    private fun toggleWishList(producto: Producto) {
        val wishList = wishListViewModel.wishList.value!!
        if (wishList.contains(producto)) {
            wishList.remove(producto)
            Toast.makeText(context, "${producto.nombre} eliminado de la lista de deseos", Toast.LENGTH_SHORT).show()
        } else {
            wishList.add(producto)
            Toast.makeText(context, "${producto.nombre} agregado a la lista de deseos", Toast.LENGTH_SHORT).show()
        }
        wishListViewModel.wishList.value = wishList
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
