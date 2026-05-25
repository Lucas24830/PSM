package com.example.psm.first_app

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.psm.R

class Historial : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_historial)

        mostrarHistorial()
    }

    private fun mostrarHistorial() {

        val contenedor =
            findViewById<LinearLayout>(
                R.id.contenedorHistorial
            )

        val dbHelper = DBHelper(this)

        val cursor =
            dbHelper.obtenerHistorial(
                Sesion.usuarioActual
            )

        while (cursor.moveToNext()) {

            val titulo = cursor.getString(2)
            val precio = cursor.getDouble(3)
            val cantidad = cursor.getInt(4)
            val total = cursor.getDouble(5)

            val card = CardView(this)

            val layout = LinearLayout(this)

            layout.orientation =
                LinearLayout.VERTICAL

            layout.setPadding(
                30,
                30,
                30,
                30
            )

            val tvTitulo = TextView(this)
            tvTitulo.text = titulo

            val tvCantidad = TextView(this)
            tvCantidad.text =
                "Cantidad: $cantidad"

            val tvPrecio = TextView(this)
            tvPrecio.text =
                "Precio: $$precio"

            val tvTotal = TextView(this)
            tvTotal.text =
                "Total: $$total"

            layout.addView(tvTitulo)
            layout.addView(tvCantidad)
            layout.addView(tvPrecio)
            layout.addView(tvTotal)

            card.addView(layout)

            contenedor.addView(card)
        }

        cursor.close()
    }
}