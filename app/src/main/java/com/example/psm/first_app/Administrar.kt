package com.example.psm.first_app

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.psm.R

class Administrar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrar)

        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        btnAgregar.setOnClickListener {
            startActivity(
                Intent(this, AgregarProducto::class.java)
            )
        }

        mostrarProductos()
    }

    override fun onResume() {
        super.onResume()
        mostrarProductos()
    }

    private fun mostrarProductos() {

        val contenedor =
            findViewById<LinearLayout>(R.id.contenedorProductos)

        contenedor.removeAllViews()

        val dbHelper = DBHelper(this)

        val cursor = dbHelper.obtenerProductos()

        while (cursor.moveToNext()) {

            val id = cursor.getInt(0)
            val titulo = cursor.getString(1)
            val descripcion = cursor.getString(2)
            val precio = cursor.getDouble(3)
            val disponible = cursor.getInt(5)

            // CARD
            val card = CardView(this)

            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            params.setMargins(0, 0, 0, 30)

            card.layoutParams = params
            card.radius = 25f
            card.cardElevation = 10f

            // LAYOUT INTERNO
            val layout = LinearLayout(this)

            layout.orientation = LinearLayout.VERTICAL

            layout.setPadding(
                40,
                40,
                40,
                40
            )

            // TITULO
            val tvTitulo = TextView(this)

            tvTitulo.text = titulo
            tvTitulo.textSize = 22f

            // DESCRIPCION
            val tvDescripcion = TextView(this)

            tvDescripcion.text = descripcion
            tvDescripcion.textSize = 16f

            // PRECIO
            val tvPrecio = TextView(this)

            tvPrecio.text = "Precio: $$precio"
            tvPrecio.textSize = 18f

            // DISPONIBLE
            val checkDisponible = CheckBox(this)

            checkDisponible.text = "Disponible"

            checkDisponible.isChecked =
                disponible == 1

            checkDisponible.setOnCheckedChangeListener { _, isChecked ->

                val valor =
                    if (isChecked) 1 else 0

                dbHelper.actualizarDisponible(
                    id,
                    valor
                )
            }

            // BOTON ELIMINAR
            val btnEliminar = Button(this)

            btnEliminar.text = "Eliminar"

            btnEliminar.setOnClickListener {

                dbHelper.eliminarProducto(id)

                mostrarProductos()
            }

            // AGREGAR VISTAS
            layout.addView(tvTitulo)
            layout.addView(tvDescripcion)
            layout.addView(tvPrecio)
            layout.addView(checkDisponible)
            layout.addView(btnEliminar)

            card.addView(layout)

            contenedor.addView(card)
        }

        cursor.close()
    }
}