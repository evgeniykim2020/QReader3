package com.evgeniykim.qreader3.data.orm

import android.content.Context
import android.database.Cursor
import android.util.Log
import com.evgeniykim.qreader3.data.DatabaseFav
import com.evgeniykim.qreader3.data.DatabaseWrapper
import com.evgeniykim.qreader3.domain.Favorite
import com.evgeniykim.qreader3.domain.History
import java.lang.Exception

class FavoriteORM: InterfaceORM<Favorite> {

    private val TAG = "FavoriteORM"
    private val TABLE_NAME = "favorite"
    private val COMMA_SEPARATOR = ", "
    private val COLUMN_ID_TYPE = "integer PRIMARY KEY AUTOINCREMENT"
    private val COLUMN_ID = "id"
    private val COLUMN_CONTEXT_TYPE = "TEXT"
    private val COLUMN_CONTEXT = "context"
    private val COLUMN_DATE_TYPE = "TEXT"
    private val COLUMN_DATE = "date"

    val SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " " + COLUMN_ID_TYPE + COMMA_SEPARATOR +
            COLUMN_DATE + " " + COLUMN_DATE_TYPE + COMMA_SEPARATOR + COLUMN_CONTEXT + " " + COLUMN_CONTEXT_TYPE + ")"

    val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"


    override fun cursorToObject(cursor: Cursor): Favorite {
        return Favorite(
            cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
            cursor.getString(cursor.getColumnIndex(COLUMN_CONTEXT))
        )
    }

    override fun add(context: Context, t: Favorite) {
        val databaseFav = DatabaseFav(context)
        val database = databaseFav.readableDatabase

        val query = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_DATE + ", " + COLUMN_CONTEXT + ") VALUES ( '" + t.date + "', '" + t.context + "' )"
        database.execSQL(query)
        database.close()
    }

    override fun clearAll(context: Context) {
        val databaseFav = DatabaseFav(context)
        val database = databaseFav.readableDatabase
        database.delete(TABLE_NAME, null, null)
    }

    override fun getAll(context: Context): MutableList<Favorite> {
        val databaseFav = DatabaseFav(context)
        val  database = databaseFav.readableDatabase
        val favoriteList = ArrayList<Favorite>()

        val cursor = database.rawQuery("SELECT * FROM $TABLE_NAME", null)
        try {
            if (cursor!!.moveToFirst()) {
                do {
                    val h = cursorToObject(cursor)
                    favoriteList.add(h)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.d(TAG, "Ошибка")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        database.close()

        return favoriteList
    }


}