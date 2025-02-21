package com.example.esencias

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarritoManager : ViewModel() {
    private val _carrito = MutableLiveData<MutableList<Producto>>(mutableListOf())
    val carrito: LiveData<MutableList<Producto>> get()= _carrito



    fun agregarProducto(producto: Producto) {
        val nuevaLista = _carrito.value ?: mutableListOf() // Obtiene la lista actual o crea una nueva
        nuevaLista.add(producto) // Agrega el nuevo producto
        _carrito.postValue(nuevaLista) // 🔹 Usar postValue para asegurar la actualización en el hilo correcto
        Log.i("CarritoManager" ,"Producto Agregado")
    }



    fun eliminarProducto(producto: Producto) {
        val nuevalista = _carrito.value?.toMutableList() ?: mutableListOf() // 🔹 Copia nueva lista
        nuevalista.remove(producto)
        _carrito.value = nuevalista.toMutableList() // ✅ LiveData detectará el cambio
    }

    fun vaciarCarrito() {
        _carrito.value = mutableListOf() // ✅ LiveData se actualiza correctamente
    }
}