package com.example.psm.first_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.psm.R
import android.widget.Toast


class SegundoAvancePSM : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_segundo_avance_psm)

        val btnVerMenu = findViewById<CardView>(R.id.btnVerMenu)

        btnVerMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val btnMiPedido = findViewById<CardView>(R.id.btnMiPedido)

        findViewById<CardView>(R.id.btnMiPedido)

        btnMiPedido.setOnClickListener {

            val intent =
                Intent(this, MiPedidoActivity::class.java)

            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnHistorial = findViewById<CardView>(R.id.btnHistorial)

        btnHistorial.setOnClickListener {
            val intent = Intent(this, Historial::class.java)
            startActivity(intent)
        }
        val btnAdministrar = findViewById<CardView>(R.id.btnAdministrar)

        btnAdministrar.setOnClickListener {

            if (Sesion.esTrabajador) {

                val intent = Intent(this, Administrar::class.java)
                startActivity(intent)

            } else {

                Toast.makeText(
                    this,
                    "Solo trabajadores pueden acceder",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}