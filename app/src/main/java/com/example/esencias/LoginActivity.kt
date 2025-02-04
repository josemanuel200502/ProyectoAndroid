package com.example.esencias

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.login_activity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Traigo los botones del layout
        val registro: TextView = findViewById(R.id.registro)
        val inicio: Button = findViewById(R.id.entrarButton)
        val invitado: Button= findViewById(R.id.invitadoButton)

        // Click listeners
        registro.setOnClickListener {
            abrirActivity(RegisterActivity::class.java)
        }
        invitado.setOnClickListener{
            abrirActivity(AppActivityInvitado::class.java)
        }

        inicio.setOnClickListener{
            iniciarSesion()
        }
    }

    private fun <T : Activity> abrirActivity(clase: Class<T>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    private fun iniciarSesion(){
        var nombre:String?=findViewById<EditText>(R.id.loginEmail).text.toString()
        val pass=findViewById<EditText>(R.id.loginPass).text.toString()
        val bd=BBDD(this)

        nombre=bd.encontrarUsuario(nombre)

        if(nombre!=null){
            val quienEs=bd.verificarUsuario(nombre,pass)
            if(quienEs[0]){
                if(quienEs[1]){
                    val intent = Intent(this, ActivityEsencias::class.java)
                    intent.putExtra("fragmento","MenuAdministrador")
                    startActivity(intent)
                }else
                    abrirActivity(AppActivity::class.java)
            }else{
                Toast.makeText(this,"Contraseña incorrecta...",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,"Usuario no encontrado",Toast.LENGTH_LONG).show()
        }
    }
}