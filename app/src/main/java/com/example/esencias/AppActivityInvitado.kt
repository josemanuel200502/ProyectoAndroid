package com.example.esencias

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class AppActivityInvitado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_invitado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // se carga el fragment de inicio al empezar la actividad
        cargarFragment(savedInstanceState,Inicio())

        // declaracion de los botones

        val botonInicio: ImageButton =findViewById(R.id.botonInicioInvitado)
        val botonCesta: ImageButton =findViewById(R.id.botonCestaInvitado)
        val botonAjustes: ImageButton =findViewById(R.id.botonAjustesInvitado)

        // click listeners para viajar entre fragmentos

        botonInicio.setOnClickListener{
            cargarFragment(savedInstanceState,Inicio())
        }

        botonCesta.setOnClickListener{
            Toast.makeText(this,"Esta opción no está disponible en modo invitado...",Toast.LENGTH_SHORT).show()
        }

        botonAjustes.setOnClickListener{
            Toast.makeText(this,"Esta opción no está disponible en modo invitado...",Toast.LENGTH_SHORT).show()
        }


    }

    // metodo para mostrar fragments

    private fun cargarFragment(savedInstanceState: Bundle?, fragment: Fragment) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}