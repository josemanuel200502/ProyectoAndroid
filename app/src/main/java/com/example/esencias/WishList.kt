package com.example.esencias

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WishList : ViewModel(){
    private val _wishList = MutableLiveData<MutableList<Producto>>(mutableListOf())
    val wishList: LiveData<MutableList<Producto>> get() = _wishList

    fun agregarProducto(producto: Producto) {
        val lista = _wishList.value ?: mutableListOf()
        lista.add(producto)
        _wishList.value = lista
    }

    fun eliminarProducto(producto: Producto) {
        val lista = _wishList.value ?: mutableListOf()
        lista.remove(producto)
        _wishList.value = lista
    }

    fun toggleWishList(producto: Producto) {
        val listaActual = _wishList.value ?: mutableListOf()

        if (listaActual.contains(producto)) {
            listaActual.remove(producto)
        } else {
            listaActual.add(producto)
        }

        _wishList.value = listaActual // Aquí sí podemos modificarlo
    }
}