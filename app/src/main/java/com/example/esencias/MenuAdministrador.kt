package com.example.esencias

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout


class MenuAdministrador : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_menu_administrador2, container, false)

        val flechaVolver:ImageView=view.findViewById(R.id.FlechaAdmin)

        val contenedorvelas: LinearLayout =view.findViewById(R.id.Contenedorvelas)
        val contenedorcursos: LinearLayout =view.findViewById(R.id.Contenedorcursos)

        contenedorvelas.setOnClickListener{
            abrirFragment(GestionVelas())
        }

        contenedorcursos.setOnClickListener{
            abrirFragment(GestionCursos())
        }

        flechaVolver.setOnClickListener{
            val intent=Intent(requireContext(),AppActivityAdmin::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun abrirFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_esencias,fragment)
            .commit()
    }
}