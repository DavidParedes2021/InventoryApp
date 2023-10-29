package com.example.secondproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    /**
     * Usage example:
     *         var db_helper = DBHelper(context = applicationContext)
     *         val db_write = db_helper.writableDatabase
     *
     *         val sortOrder = "${DBContract.FeedEntry.COLUMN_NAME_NAME} DESC"
     *         val cursor = db_write.query(
     *             DBContract.FeedEntry.TABLE_NAME,   // The table to query
     *             null,             // The array of columns to return (pass null to get all)
     *             null,              // The columns for the WHERE clause
     *             null,          // The values for the WHERE clause
     *             null,                   // don't group the rows
     *             null,                   // don't filter by row groups
     *             sortOrder               // The sort order
     *         )
     *
     *         var db_in_string = ""
     *         with(cursor) {
     *             while (moveToNext()) {
     *                 val name = getString(getColumnIndexOrThrow(DBContract.FeedEntry.COLUMN_NAME_NAME))
     *                 val price = getString(getColumnIndexOrThrow(DBContract.FeedEntry.COLUMN_NAME_PRICE))
     *                 db_in_string += "$name - $price\n"
     *             }
     *         }
     *         cursor.close()
     */

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${DBContract.FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${DBContract.FeedEntry.COLUMN_NAME_NAME} TEXT," +
                "${DBContract.FeedEntry.COLUMN_NAME_PRICE} DOUBLE," +
                "${DBContract.FeedEntry.COLUMN_NAME_QUANTITY} INTEGER)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DBContract.FeedEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insert(name: String, price:Double, quantity: Int) {
        val values = ContentValues().apply {
            put(DBContract.FeedEntry.COLUMN_NAME_NAME, name)
            put(DBContract.FeedEntry.COLUMN_NAME_PRICE, price)
            put(DBContract.FeedEntry.COLUMN_NAME_QUANTITY, quantity)
        }
        val db = this.writableDatabase
        db?.insert(DBContract.FeedEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun update(productId: Int, name: String, price: Double, quantity: Int) {
        val data = ContentValues()
        data.put(DBContract.FeedEntry.COLUMN_NAME_NAME, name)
        data.put(DBContract.FeedEntry.COLUMN_NAME_PRICE, price)
        data.put(DBContract.FeedEntry.COLUMN_NAME_QUANTITY, quantity)

        val db = this.writableDatabase
        db.update(DBContract.FeedEntry.TABLE_NAME , data, "id=?", arrayOf(productId.toString()))
        db.close()
    }

    fun delete(productId: Int) {
        val db = this.writableDatabase
        db.delete(DBContract.FeedEntry.TABLE_NAME , "id=?", arrayOf(productId.toString()))
        db.close()
    }

    fun getProducts(): List<Product> {
        val productsList = mutableListOf<Product>()
        val db: SQLiteDatabase = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM products", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val price = cursor.getDouble(2)
                val quantity = cursor.getInt(3)

                val product = Product(id, name, price, quantity)
                productsList.add(product)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return productsList
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Examen2.db"
    }
}