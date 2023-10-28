package com.example.examen2_take2

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
                "${DBContract.FeedEntry.COLUMN_NAME_PRICE} INTEGER)"

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

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Examen2.db"
    }
}