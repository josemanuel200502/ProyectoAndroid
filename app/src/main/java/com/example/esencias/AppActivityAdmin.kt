package com.example.esencias

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class AppActivityAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_admin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // se carga el fragment de MenuAdministrador al empezar la actividad
        cargarFragment(savedInstanceState,Inicio())

        // declaracion de los botones

        val botonCesta: ImageButton =findViewById(R.id.botonCestaAdmin)
        val botonInicio: ImageButton =findViewById(R.id.botonInicioAdmin)
        val botonAjustes: ImageButton =findViewById(R.id.botonAjustesAdmin)
        val botonAdmin: ImageButton =findViewById(R.id.botonAdmin)

        // click listeners para viajar entre fragmentos

        botonAdmin.setOnClickListener{
            val intent = Intent(this, ActivityEsencias::class.java)
            intent.putExtra("fragmento","MenuAdministrador")
            startActivity(intent)
        }

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