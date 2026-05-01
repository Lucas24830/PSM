package com.example.psm.first_app

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.psm.R

class Usuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        val dbHelper = DBHelper(this)

        val rgTipo = findViewById<RadioGroup>(R.id.rgTipo)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etRol = findViewById<EditText>(R.id.etRol)
        val cbActivo = findViewById<CheckBox>(R.id.cbActivo)
        val btnCrear = findViewById<Button>(R.id.btnCrear)

        // Control de visibilidad de campos extras
        rgTipo.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbTrabajador) {
                etRol.visibility = View.VISIBLE
                cbActivo.visibility = View.VISIBLE
            } else {
                etRol.visibility = View.GONE
                cbActivo.visibility = View.GONE
            }
        }

        btnCrear.setOnClickListener {
            val db = dbHelper.writableDatabase
            val nombre = etNombre.text.toString()
            val telefono = etTelefono.text.toString()
            val usuario = etUsuario.text.toString()
            val password = etPassword.text.toString()

            val values = ContentValues()

            if (rgTipo.checkedRadioButtonId == R.id.rbCliente) {
                values.put(DBHelper.COLUMN_CLI_NOMBRE, nombre)
                values.put(DBHelper.COLUMN_CLI_TELEFONO, telefono)
                values.put(DBHelper.COLUMN_CLI_USUARIO, usuario)
                values.put(DBHelper.COLUMN_CLI_PASSWORD, password)

                db.insert(DBHelper.TABLE_CLIENTES, null, values)
                Toast.makeText(this, "Cliente guardado", Toast.LENGTH_SHORT).show()

            } else if (rgTipo.checkedRadioButtonId == R.id.rbTrabajador) {
                val rol = etRol.text.toString()
                val activo = if (cbActivo.isChecked) 1 else 0

                values.put(DBHelper.COLUMN_TRA_NOMBRE, nombre)
                values.put(DBHelper.COLUMN_TRA_TELEFONO, telefono)
                values.put(DBHelper.COLUMN_TRA_ROL, rol)
                values.put(DBHelper.COLUMN_TRA_ACTIVO, activo)
                values.put(DBHelper.COLUMN_TRA_USUARIO, usuario)
                values.put(DBHelper.COLUMN_TRA_PASSWORD, password)

                db.insert(DBHelper.TABLE_TRABAJADORES, null, values)
                Toast.makeText(this, "Trabajador guardado", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Selecciona un tipo de usuario", Toast.LENGTH_SHORT).show()
            }
           // db.close()
        }
    }
}