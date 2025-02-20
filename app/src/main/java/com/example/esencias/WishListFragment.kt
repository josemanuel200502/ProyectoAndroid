package com.example.esencias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WishListFragment : Fragment() {

    private lateinit var wishListViewModel: WishList
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptador: Adaptador

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        wishListViewModel = ViewModelProvider(requireActivity()).get(WishList::class.java)
        val wishList = wishListViewModel.wishList.value!!

        adaptador = Adaptador(wishList,
            onItemClick = { producto ->
                // Define qué hacer cuando se hace clic en un producto en la lista de deseos
            },
            onAgregarClick = { producto ->
                // Define qué hacer cuando se hace clic en el botón "Agregar" en la lista de deseos
            }
        )

        recyclerView = view.findViewById(R.id.recycler_wishlist)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adaptador

        // Observa los cambios en la wishList y actualiza el adaptador
        wishListViewModel.wishList.observe(viewLifecycleOwner, { nuevaLista ->
            adaptador.notifyDataSetChanged()
        })

        return view
    }
}
