package com.example.esencias
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarritoFragment : Fragment () {

    private lateinit var carritoManager: CarritoManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptador: Adaptador
    private lateinit var btnComprar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_carrito, container, false)
        carritoManager = ViewModelProvider(requireActivity()).get(CarritoManager::class.java)

        recyclerView = view.findViewById(R.id.recycler_carrito)
        btnComprar = view.findViewById(R.id.btn_comprar)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adaptador = Adaptador(mutableListOf(),
            onItemClick = {/* Implementar si es necesario */ },
            onAgregarClick = {/* Implementar si es necesario */ },
            onAgregarAlCarritoClick = { producto ->
                carritoManager.agregarProducto(producto)
            },
            onEliminarClick = { producto ->
                carritoManager.eliminarProducto(producto)
            }
        )

        recyclerView.adapter = adaptador

        // 🔥 Observamos el LiveData y actualizamos la lista cuando cambia
        carritoManager.carrito.observe(viewLifecycleOwner) { nuevaLista ->
            Log.d("CarritoFragment", "Lista actualizada con ${nuevaLista.size} productos")
            adaptador.actualizarLista(nuevaLista)
        }

        btnComprar.setOnClickListener {
            realizarCompra()
        }

        return view
    }

    private fun realizarCompra() {
        val db = BBDD(requireContext())
        val listaProductos = carritoManager.carrito.value ?: mutableListOf()
        if (listaProductos.isNotEmpty()) {
            val usuarioCorreo = obtenerCorreoUsuarioActual() // Método para obtener el correo del usuario actual como cadena
            val compra = Compra(
                id = 0,
                usuarioId = usuarioCorreo, // Asignar el correo del usuario actual
                listaProductos = listaProductos,
                fechaCompra = System.currentTimeMillis()
            )
            db.agregarCompra(compra)
            carritoManager.vaciarCarrito()
            Toast.makeText(context, "Compra realizada con éxito", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "El carrito está vacío", Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtenerCorreoUsuarioActual(): String {
        val sharedPreferences = requireContext().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("usuarioCorreo", "") ?: ""
    }


}

