package com.example.psm.first_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.psm.R
import android.content.Intent
import android.widget.Button

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        val btnLogin = findViewById<Button>(R.id.btnIngresar)
        val btnRegistro = findViewById<Button>(R.id.btnCrearCuenta)

        btnLogin.setOnClickListener {
            val intent = Intent(this, SegundoAvancePSM::class.java)
            startActivity(intent)
        }

        btnRegistro.setOnClickListener {
            val intent = Intent(this, Usuario::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}