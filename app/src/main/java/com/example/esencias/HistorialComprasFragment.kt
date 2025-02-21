package com.example.esencias

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistorialComprasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptador: HistorialComprasAdapter
    private lateinit var db: BBDD

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historial_compras, container, false)
        db = BBDD(requireContext())
        recyclerView = view.findViewById(R.id.recycler_historial_compras)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Obtener el correo del usuario actual
        val usuarioCorreo = obtenerCorreoUsuarioActual()

        // Pasar el correo en lugar de un entero
        val historialCompras = db.obtenerHistorialCompras(usuarioCorreo)
        adaptador = HistorialComprasAdapter(historialCompras)
        recyclerView.adapter = adaptador

        return view
    }

    private fun obtenerCorreoUsuarioActual(): String {
        val sharedPreferences = requireContext().getSharedPreferences("MiAppPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("usuarioCorreo", "") ?: ""
    }
}
