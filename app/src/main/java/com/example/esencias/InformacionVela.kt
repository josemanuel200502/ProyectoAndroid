package com.example.esencias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Inicio : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        // Recuperamos la lista de velas y limitamos a 3 elementos
        val db = BBDD(requireContext())
        val listaVelas = db.listaVelas().take(3) // Solo 3 velas

        // Configurar RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerVelas)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = AdaptadorInicio(listaVelas) { vela ->
            mostrarInfoVela(vela)
        }

        // Configurar botón "Más velas"
        val masVelas: ImageView = view.findViewById(R.id.Mas)
        masVelas.setOnClickListener {
            cargarFragment(VelasFragment()) // Abre el fragmento de todas las velas
        }

        // Configurar botón "Información curso"
        val infoCurso: ImageView = view.findViewById(R.id.FotoCurso)
        infoCurso.setOnClickListener {
            cargarFragment(InformacionCurso()) // Abre información del curso
        }

        return view
    }
    private fun mostrarInfoVela(vela:Vela) {
        val fragment=InformacionVela()
        val bundle=Bundle()
        bundle.putParcelable("vela",vela)
        fragment.arguments=bundle
        cargarFragment(fragment)
    }

    private fun cargarFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Permite volver atrás
            .commit()
    }
}