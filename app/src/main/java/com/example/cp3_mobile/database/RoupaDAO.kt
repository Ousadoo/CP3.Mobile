package com.example.cp3_mobile.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cp3_mobile.model.Roupa

class RoupaDAO(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "roupas_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_ROUPAS = "roupas"

        private const val COLUMN_ID = "id"
        private const val COLUMN_NOME = "nome"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_TAMANHO = "tamanho"
        private const val COLUMN_COR = "cor"
        private const val COLUMN_MARCA = "marca"
        private const val COLUMN_PRECO = "preco"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_ROUPAS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME TEXT,
                $COLUMN_TIPO TEXT,
                $COLUMN_TAMANHO TEXT,
                $COLUMN_COR TEXT,
                $COLUMN_MARCA TEXT,
                $COLUMN_PRECO TEXT
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ROUPAS")
        onCreate(db)
    }

    fun inserirRoupa(roupa: Roupa): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, roupa.nome)
            put(COLUMN_TIPO, roupa.tipo)
            put(COLUMN_TAMANHO, roupa.tamanho)
            put(COLUMN_COR, roupa.cor)
            put(COLUMN_MARCA, roupa.marca)
            put(COLUMN_PRECO, roupa.preco)
        }
        return db.insert(TABLE_ROUPAS, null, values).also { db.close() }
    }

    fun atualizarRoupa(roupa: Roupa): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, roupa.nome)
            put(COLUMN_TIPO, roupa.tipo)
            put(COLUMN_TAMANHO, roupa.tamanho)
            put(COLUMN_COR, roupa.cor)
            put(COLUMN_MARCA, roupa.marca)
            put(COLUMN_PRECO, roupa.preco)
        }
        return db.update(TABLE_ROUPAS, values, "$COLUMN_ID=?", arrayOf(roupa.id.toString())).also { db.close() }
    }

    fun deletarRoupa(id: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_ROUPAS, "$COLUMN_ID=?", arrayOf(id.toString())).also { db.close() }
    }

    fun listarRoupas(): List<Roupa> {
        val listaRoupas = mutableListOf<Roupa>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ROUPAS", null)
        if (cursor.moveToFirst()) {
            do {
                val roupa = Roupa(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
                    tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO)),
                    tamanho = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAMANHO)),
                    cor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COR)),
                    marca = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MARCA)),
                    preco = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRECO))
                )
                listaRoupas.add(roupa)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaRoupas
    }
    fun obterRoupaPorId(id: Long): Roupa? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_ROUPAS,
            null,
            "$COLUMN_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var roupa: Roupa? = null
        if (cursor.moveToFirst()) {
            roupa = Roupa(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
                tipo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO)),
                tamanho = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAMANHO)),
                cor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COR)),
                marca = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MARCA)),
                preco = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRECO))
            )
        }
        cursor.close()
        db.close()
        return roupa
    }

}