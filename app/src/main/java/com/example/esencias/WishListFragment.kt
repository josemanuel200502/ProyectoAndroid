package com.example.esencias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WishListFragment : Fragment() {

    private lateinit var wishListViewModel: WishList
    private lateinit var carritoManager: CarritoManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptador: Adaptador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        wishListViewModel = ViewModelProvider(requireActivity()).get(WishList::class.java)
        carritoManager = ViewModelProvider(requireActivity()).get(CarritoManager::class.java)

        recyclerView = view.findViewById(R.id.recycler_wishlist)
        recyclerView.layoutManager = LinearLayoutManager(context)

        wishListViewModel.wishList.observe(viewLifecycleOwner, { listaProductos ->
            adaptador = Adaptador(
                listaProductos,
                onItemClick = { /* Implementar si es necesario */ },
                onAgregarClick = { /* Implementar si es necesario */ },
                onEliminarClick = { producto ->
                    wishListViewModel.eliminarProducto(producto)
                    adaptador.notifyDataSetChanged()
                },
                onAgregarAlCarritoClick = { producto ->
                    carritoManager.agregarProducto(producto)
                    wishListViewModel.eliminarProducto(producto)
                    adaptador.notifyDataSetChanged()
                    Toast.makeText(context, "${producto.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
                }
            )
            recyclerView.adapter = adaptador
        })

        return view
    }
}





