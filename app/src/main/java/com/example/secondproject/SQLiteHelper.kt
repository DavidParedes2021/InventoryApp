package com.example.secondproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.Editable

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context , "agenda_info", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val script = "CREATE TABLE agendaInfo "+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT,"+
                "email TEXT)"
        db!!.execSQL(script) //Signos: Puede ser que db sea exception
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val script = "DROP TABLE IF EXISTS agendaInfo"
        db!!.execSQL(script)
        onCreate(db)
    }

    fun insert(name: String, email:String){
        val data = ContentValues()
        data.put("name", name) //metemos los parametros a ese objeto
        data.put("email", email)

        val db = this.writableDatabase //coneste objeto podremos escribir en la bd
        db.insert("agendaInfo", null, data)
        db.close()
    }


}