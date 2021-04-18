package com.example.crud.data.dataBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.crud.data.model.Contato

class DataBaseHandler(context: Context?) : SQLiteOpenHelper(context,
    BANCO, null, 1) {

    companion object{
        private var TABLE = "TBL_CONTATO"
        private var ID = "id"
        private var NOME = "nome"
        private var TELEFONE = "telefone"
        private var BANCO = "dbContato"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create = "CREATE TABLE $TABLE (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "$NOME TEXT NOT NULL," +
                "$TELEFONE TEXT NOT NULL)"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun insertContato(contato: Contato) {
        var db = writableDatabase
        var values = ContentValues()
        values.put(NOME, contato.nome)
        values.put(TELEFONE, contato.telefone)
        db.insert(TABLE, null, values)
        db.close()
    }

    fun selectContato(): ArrayList<Contato> {
        var db = readableDatabase
        var sql = "SELECT * FROM $TABLE"
        var cursor = db.rawQuery(sql, null)
        var arrayList = ArrayList<Contato>()
        cursorContato(cursor, arrayList)

        db.close()
        return arrayList
    }

    private fun cursorContato(cursor: Cursor, arrayList: ArrayList<Contato>) {
        while (cursor.moveToNext()){
            var contato = Contato(
                cursor.getInt(cursor.getColumnIndex(ID)),
                cursor.getString(cursor.getColumnIndex(NOME)),
                cursor.getString(cursor.getColumnIndex(TELEFONE))
            )

            arrayList.add(contato)
        }
    }

    fun updateContato(index: Int, contato: Contato) {
        var db = writableDatabase
        var where = "$ID = ?"
        var args= arrayOf("$index")
        var values = ContentValues()
        values.put(NOME, contato.nome)
        values.put(TELEFONE, contato.telefone)
        db.update(TABLE, values, where, args)
        db.close()
    }

    fun deleteContato(index: Int) {
        var db = writableDatabase
        var where = "$ID = ?"
        var args = arrayOf("$index")
        db.delete(TABLE, where, args)
        db.close()
    }

    fun likeContato(nome:String): ArrayList<Contato> {
        var db = readableDatabase
        var sql = "SELECT * FROM $TABLE WHERE $NOME LIKE ?"
        var args = arrayOf("%$nome%")
        var cursor = db.rawQuery(sql, args)
        var arrayList = ArrayList<Contato>()
        cursorContato(cursor, arrayList)

        db.close()
        return arrayList
    }
}