package com.example.esencias

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class Inicio : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        val masVelas: ImageView = view.findViewById(R.id.Mas)

        masVelas.setOnClickListener {
            cargarFragment(VelasFragment())
        }

        val infoCurso: ImageView = view.findViewById(R.id.FotoCurso)

        infoCurso.setOnClickListener{
            cargarFragment(InformacionCurso())
        }

        return view
    }

    private fun cargarFragment(fragment:Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}