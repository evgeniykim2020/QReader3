package com.evgeniykim.qreader3.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.evgeniykim.qreader3.data.orm.FavoriteORM

class DatabaseFav(context: Context): SQLiteOpenHelper(context, "fav.db", null, 1) {

    private val TAG = javaClass.name
    private val DB_NAME = "fav.db"
    private val DB_VERSION = 1

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(FavoriteORM().SQL_CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(FavoriteORM().SQL_DROP_TABLE)
    }
}