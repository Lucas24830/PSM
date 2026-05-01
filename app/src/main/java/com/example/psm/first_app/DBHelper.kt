package com.example.psm.first_app

import android.content.Context
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
        private const val DATABASE_VERSION = 2

        // Constantes para Clientes
        const val TABLE_CLIENTES = "clientes"
        const val COLUMN_CLI_NOMBRE = "nombre"
        const val COLUMN_CLI_TELEFONO = "telefono"
        const val COLUMN_CLI_USUARIO = "usuario"
        const val COLUMN_CLI_PASSWORD = "password"

        // Constantes para Trabajadores
        const val TABLE_TRABAJADORES = "trabajadores"
        const val COLUMN_TRA_NOMBRE = "nombre"
        const val COLUMN_TRA_TELEFONO = "telefono"
        const val COLUMN_TRA_ROL = "rol"
        const val COLUMN_TRA_ACTIVO = "activo"
        const val COLUMN_TRA_USUARIO = "usuario"
        const val COLUMN_TRA_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_CLIENTES (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_CLI_NOMBRE TEXT, " +
                    "$COLUMN_CLI_TELEFONO TEXT, " +
                    "$COLUMN_CLI_USUARIO TEXT, " +
                    "$COLUMN_CLI_PASSWORD TEXT)"
        )

        db.execSQL(
            "CREATE TABLE $TABLE_TRABAJADORES (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TRA_NOMBRE TEXT, " +
                    "$COLUMN_TRA_TELEFONO TEXT, " +
                    "$COLUMN_TRA_ROL TEXT, " +
                    "$COLUMN_TRA_ACTIVO INTEGER, " +
                    "$COLUMN_TRA_USUARIO TEXT, " +
                    "$COLUMN_TRA_PASSWORD TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRABAJADORES")
        onCreate(db)
    }
}