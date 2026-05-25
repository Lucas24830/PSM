package com.example.psm.first_app

import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.psm.R

class AgregarProducto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        val dbHelper = DBHelper(this)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)

        val spCategoria = findViewById<Spinner>(R.id.spCategoria)

        val cbDisponible = findViewById<CheckBox>(R.id.cbDisponible)

        val btnGuardar = findViewById<Button>(R.id.btnGuardarProducto)

        // Categorías
        val categorias = arrayOf(
            "Burritos",
            "Bebidas",
            "Postres"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )

        spCategoria.adapter = adapter

        // GUARDAR PRODUCTO
        btnGuardar.setOnClickListener {

            val titulo = etTitulo.text.toString()
            val precio = etPrecio.text.toString()
            val descripcion = etDescripcion.text.toString()

            val categoria = spCategoria.selectedItem.toString()

            val disponible = if (cbDisponible.isChecked) 1 else 0

            val db = dbHelper.writableDatabase

            val values = ContentValues()

            values.put(DBHelper.COLUMN_PROD_TITULO, titulo)
            values.put(DBHelper.COLUMN_PROD_PRECIO, precio)
            values.put(DBHelper.COLUMN_PROD_DESCRIPCION, descripcion)
            values.put(DBHelper.COLUMN_PROD_CATEGORIA, categoria)
            values.put(DBHelper.COLUMN_PROD_DISPONIBLE, disponible)

            db.insert(DBHelper.TABLE_PRODUCTOS, null, values)

            Toast.makeText(
                this,
                "Producto guardado",
                Toast.LENGTH_SHORT
            ).show()

            db.close()

            finish()
        }
    }
}