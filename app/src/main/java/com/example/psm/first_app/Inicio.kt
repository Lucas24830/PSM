package com.example.psm.first_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.psm.R

class Inicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnLogin = findViewById<Button>(R.id.btnIngresar)
        val btnRegistro = findViewById<Button>(R.id.btnCrearCuenta)

        val dbHelper = DBHelper(this)

        // BOTON INGRESAR
        btnLogin.setOnClickListener {

            val usuario = etUsuario.text.toString()
            val password = etPassword.text.toString()

            val db = dbHelper.readableDatabase

            // Buscar trabajador
            val cursorTrabajador = db.rawQuery(
                "SELECT * FROM trabajadores WHERE usuario=? AND password=?",
                arrayOf(usuario, password)
            )

            if (cursorTrabajador.moveToFirst()) {

                Sesion.esTrabajador = true
                Sesion.usuarioActual = usuario

                Toast.makeText(
                    this,
                    "Bienvenido trabajador",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(this, SegundoAvancePSM::class.java)
                )

            } else {

                // Buscar cliente
                val cursorCliente = db.rawQuery(
                    "SELECT * FROM clientes WHERE usuario=? AND password=?",
                    arrayOf(usuario, password)
                )

                if (cursorCliente.moveToFirst()) {

                    Sesion.esTrabajador = false

                    Sesion.usuarioActual = usuario


                    Toast.makeText(
                        this,
                        "Bienvenido cliente",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(this, SegundoAvancePSM::class.java)
                    )

                } else {

                    Toast.makeText(
                        this,
                        "Usuario o contraseña incorrectos",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                cursorCliente.close()
            }

            cursorTrabajador.close()
        }

        // BOTON CREAR CUENTA
        btnRegistro.setOnClickListener {

            startActivity(
                Intent(this, Usuario::class.java)
            )
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }
}