package com.example.esencias

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WishListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptador: Adaptador
    private val wishList = mutableListOf<Producto>() // Esta lista será compartida desde VelasFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        recyclerView = view.findViewById(R.id.recycler_wishlist)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adaptador = Adaptador(wishList,
            onItemClick = { producto ->
                // Define qué hacer cuando se hace clic en un producto en la lista de deseos
            },
            onAgregarClick = { producto ->
                // Define qué hacer cuando se hace clic en el botón "Agregar" en la lista de deseos
            }
        )

        recyclerView.adapter = adaptador

        return view
    }

    fun actualizarWishList(nuevaLista: List<Producto>) {
        wishList.clear()
        wishList.addAll(nuevaLista)
        adaptador.notifyDataSetChanged()
    }
}
