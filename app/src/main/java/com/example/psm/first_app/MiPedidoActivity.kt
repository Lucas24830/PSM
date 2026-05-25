package com.example.psm.first_app

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.psm.R

class MiPedidoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mi_pedido)

        mostrarPedidos()
    }

    private fun mostrarPedidos() {

        val contenedor =
            findViewById<LinearLayout>(R.id.contenedorPedidos)

        val tvTotal =
            findViewById<TextView>(R.id.tvTotal)

        val btnRealizar =
            findViewById<Button>(R.id.btnRealizarPedido)

        contenedor.removeAllViews()

        val dbHelper = DBHelper(this)

        val cursor =
            dbHelper.obtenerPedidos(
                Sesion.usuarioActual
            )

        var total = 0.0

        while (cursor.moveToNext()) {

            val titulo = cursor.getString(2)
            val precio = cursor.getDouble(3)
            val cantidad = cursor.getInt(4)

            val subtotal =
                precio * cantidad

            total += subtotal

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

            val tvSubtotal = TextView(this)
            tvSubtotal.text =
                "Subtotal: $$subtotal"

            layout.addView(tvTitulo)
            layout.addView(tvCantidad)
            layout.addView(tvSubtotal)

            card.addView(layout)

            contenedor.addView(card)
        }

        tvTotal.text = "Total: $$total"

        btnRealizar.setOnClickListener {

            Toast.makeText(
                this,
                "Pedido realizado",
                Toast.LENGTH_SHORT
            ).show()

            val cursorGuardar =
                dbHelper.obtenerPedidos(
                    Sesion.usuarioActual
                )

            while (cursorGuardar.moveToNext()) {

                val titulo = cursorGuardar.getString(2)
                val precio = cursorGuardar.getDouble(3)
                val cantidad = cursorGuardar.getInt(4)

                val subtotal = precio * cantidad

                dbHelper.guardarHistorial(
                    Sesion.usuarioActual,
                    titulo,
                    precio,
                    cantidad,
                    subtotal
                )
            }

            cursorGuardar.close()

            dbHelper.limpiarPedidos(
                Sesion.usuarioActual
            )

            recreate()
        }

        cursor.close()
    }
}