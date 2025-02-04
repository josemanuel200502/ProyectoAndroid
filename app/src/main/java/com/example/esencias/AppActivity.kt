package com.example.esencias

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class AppActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // se carga el fragment de inicio al empezar la actividad
        cargarFragment(savedInstanceState,Inicio())

        // declaracion de los botones

        val botonCesta: ImageButton=findViewById(R.id.botonCesta)
        val botonInicio: ImageButton=findViewById(R.id.botonInicio)
        val botonAjustes: ImageButton=findViewById(R.id.botonAjustes)

        // click listeners para viajar entre fragmentos

        botonCesta.setOnClickListener{
            cargarFragment(savedInstanceState, CestaFragment())
        }

        botonInicio.setOnClickListener{
            cargarFragment(savedInstanceState,Inicio())
        }

        botonAjustes.setOnClickListener{
            cargarFragment(savedInstanceState,SettingsFragment())
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