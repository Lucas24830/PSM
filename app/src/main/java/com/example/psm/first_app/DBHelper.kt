package com.example.psm.first_app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {

        private const val DATABASE_NAME = "PSM.bd"

        private const val DATABASE_VERSION = 6

        // CLIENTES
        const val TABLE_CLIENTES = "clientes"

        const val COLUMN_CLI_NOMBRE = "nombre"
        const val COLUMN_CLI_TELEFONO = "telefono"
        const val COLUMN_CLI_USUARIO = "usuario"
        const val COLUMN_CLI_PASSWORD = "password"

        // TRABAJADORES
        const val TABLE_TRABAJADORES = "trabajadores"

        const val COLUMN_TRA_NOMBRE = "nombre"
        const val COLUMN_TRA_TELEFONO = "telefono"
        const val COLUMN_TRA_ROL = "rol"
        const val COLUMN_TRA_ACTIVO = "activo"
        const val COLUMN_TRA_USUARIO = "usuario"
        const val COLUMN_TRA_PASSWORD = "password"

        // PRODUCTOS
        const val TABLE_PRODUCTOS = "productos"

        const val COLUMN_PROD_ID = "id"
        const val COLUMN_PROD_TITULO = "titulo"
        const val COLUMN_PROD_DESCRIPCION = "descripcion"
        const val COLUMN_PROD_PRECIO = "precio"
        const val COLUMN_PROD_CATEGORIA = "categoria"
        const val COLUMN_PROD_DISPONIBLE = "disponible"

        // PEDIDOS
        const val TABLE_PEDIDOS = "pedidos"

        const val COLUMN_PED_ID = "id"
        const val COLUMN_PED_TOTAL = "total"

        // DETALLE PEDIDOS
        const val TABLE_DETALLE = "detalle_pedidos"

        const val COLUMN_DET_ID = "id"
        const val COLUMN_DET_TITULO = "titulo"
        const val COLUMN_DET_PRECIO = "precio"
        const val COLUMN_DET_CANTIDAD = "cantidad"
    }

    override fun onCreate(db: SQLiteDatabase) {

        // TABLA CLIENTES
        db.execSQL(
            """
            CREATE TABLE clientes(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                telefono TEXT,
                usuario TEXT,
                password TEXT
            )
            """.trimIndent()
        )

        // TABLA TRABAJADORES
        db.execSQL(
            """
            CREATE TABLE trabajadores(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                telefono TEXT,
                rol TEXT,
                activo INTEGER,
                usuario TEXT,
                password TEXT
            )
            """.trimIndent()
        )

        // TABLA PRODUCTOS
        db.execSQL(
            """
            CREATE TABLE productos(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT,
                descripcion TEXT,
                precio REAL,
                categoria TEXT,
                disponible INTEGER
            )
            """.trimIndent()
        )

        // TABLA PEDIDOS
        db.execSQL(
            """
            CREATE TABLE pedidos(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                total REAL
            )
            """.trimIndent()
        )

        // TABLA DETALLE PEDIDOS
        db.execSQL(
            """
            CREATE TABLE detalle_pedidos(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT,
                titulo TEXT,
                precio REAL,
                cantidad INTEGER
            )
            """.trimIndent()
        )

        db.execSQL(
            """
    CREATE TABLE historial(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        usuario TEXT,
        titulo TEXT,
        precio REAL,
        cantidad INTEGER,
        total REAL
    )
    """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS clientes")

        db.execSQL("DROP TABLE IF EXISTS trabajadores")

        db.execSQL("DROP TABLE IF EXISTS productos")

        db.execSQL("DROP TABLE IF EXISTS pedidos")

        db.execSQL("DROP TABLE IF EXISTS detalle_pedidos")

        db.execSQL("DROP TABLE IF EXISTS historial")

        onCreate(db)
    }

    // LOGIN
    fun validarUsuario(
        usuario: String,
        password: String
    ): Boolean {

        val db = readableDatabase

        val cursorClientes = db.rawQuery(
            "SELECT * FROM clientes WHERE usuario=? AND password=?",
            arrayOf(usuario, password)
        )

        val cursorTrabajadores = db.rawQuery(
            "SELECT * FROM trabajadores WHERE usuario=? AND password=?",
            arrayOf(usuario, password)
        )

        val existe =
            cursorClientes.count > 0 ||
                    cursorTrabajadores.count > 0

        cursorClientes.close()
        cursorTrabajadores.close()

        return existe
    }

    // VALIDAR TRABAJADOR
    fun esTrabajador(
        usuario: String,
        password: String
    ): Boolean {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM trabajadores WHERE usuario=? AND password=?",
            arrayOf(usuario, password)
        )

        val existe = cursor.count > 0

        cursor.close()

        return existe
    }

    // OBTENER PRODUCTOS
    fun obtenerProductos(): Cursor {

        val db = readableDatabase

        return db.rawQuery(
            "SELECT * FROM productos",
            null
        )
    }

    // ELIMINAR PRODUCTO
    fun eliminarProducto(id: Int) {

        val db = writableDatabase

        db.delete(
            "productos",
            "id=?",
            arrayOf(id.toString())
        )
    }

    // ACTUALIZAR DISPONIBLE
    fun actualizarDisponible(
        id: Int,
        disponible: Int
    ) {

        val db = writableDatabase

        val values = ContentValues()

        values.put(
            "disponible",
            disponible
        )

        db.update(
            "productos",
            values,
            "id=?",
            arrayOf(id.toString())
        )
    }

    // AGREGAR PEDIDO
    fun agregarPedido(
        usuario: String,
        titulo: String,
        precio: Double,
        cantidad: Int
    ) {

        val db = writableDatabase

        val values = ContentValues()

        values.put("usuario", usuario)
        values.put("titulo", titulo)
        values.put("precio", precio)
        values.put("cantidad", cantidad)

        db.insert(
            "detalle_pedidos",
            null,
            values
        )
    }

    // OBTENER PEDIDOS
    fun obtenerPedidos(usuario: String): Cursor {

        val db = readableDatabase

        return db.rawQuery(
            "SELECT * FROM detalle_pedidos WHERE usuario=?",
            arrayOf(usuario)
        )
    }

    // LIMPIAR PEDIDOS
    fun limpiarPedidos(usuario: String) {

        val db = writableDatabase

        db.delete(
            "detalle_pedidos",
            "usuario=?",
            arrayOf(usuario)
        )
    }

    fun guardarHistorial(
        usuario: String,
        titulo: String,
        precio: Double,
        cantidad: Int,
        total: Double
    ) {

        val db = writableDatabase

        val values = ContentValues()

        values.put("usuario", usuario)
        values.put("titulo", titulo)
        values.put("precio", precio)
        values.put("cantidad", cantidad)
        values.put("total", total)

        db.insert(
            "historial",
            null,
            values
        )
    }

    fun obtenerHistorial(usuario: String): Cursor {

        val db = readableDatabase

        return db.rawQuery(
            "SELECT * FROM historial WHERE usuario=?",
            arrayOf(usuario)
        )
    }
}