package com.example.psm.first_app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.psm.R

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu)

        mostrarMenu()
    }

    private fun mostrarMenu() {

        val contenedor =
            findViewById<LinearLayout>(R.id.contenedorMenu)

        contenedor.removeAllViews()

        val dbHelper = DBHelper(this)

        val cursor = dbHelper.obtenerProductos()

        while (cursor.moveToNext()) {

            val titulo = cursor.getString(1)
            val descripcion = cursor.getString(2)
            val precio = cursor.getDouble(3)
            val disponible = cursor.getInt(5)

            // SOLO disponibles
            if (disponible == 1) {

                val card = CardView(this)

                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                params.setMargins(0, 0, 0, 30)

                card.layoutParams = params
                card.radius = 25f
                card.cardElevation = 8f

                val layout = LinearLayout(this)

                layout.orientation = LinearLayout.VERTICAL

                layout.setPadding(
                    30,
                    30,
                    30,
                    30
                )

                // TITULO
                val tvTitulo = TextView(this)

                tvTitulo.text = titulo
                tvTitulo.textSize = 22f

                // DESCRIPCION
                val tvDescripcion = TextView(this)

                tvDescripcion.text = descripcion

                // PRECIO
                val tvPrecio = TextView(this)

                tvPrecio.text = "Precio: $$precio"

                // CANTIDAD
                val etCantidad = EditText(this)

                etCantidad.hint = "Cantidad"

                etCantidad.inputType =
                    android.text.InputType.TYPE_CLASS_NUMBER

                // BOTON PEDIR
                val btnPedir = Button(this)

                btnPedir.text = "Pedir"

                btnPedir.setOnClickListener {

                    val cantidadTexto =
                        etCantidad.text.toString()

                    if (cantidadTexto.isEmpty()) {

                        Toast.makeText(
                            this,
                            "Ingresa una cantidad",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        val cantidad =
                            cantidadTexto.toInt()

                        dbHelper.agregarPedido(
                            Sesion.usuarioActual,
                            titulo,
                            precio,
                            cantidad
                        )

                        Toast.makeText(
                            this,
                            "Pedido agregado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                // AGREGAR
                layout.addView(tvTitulo)
                layout.addView(tvDescripcion)
                layout.addView(tvPrecio)
                layout.addView(etCantidad)
                layout.addView(btnPedir)

                card.addView(layout)

                contenedor.addView(card)
            }
        }

        cursor.close()
    }
}