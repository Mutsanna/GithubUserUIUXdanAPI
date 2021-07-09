package com.mutsanna.githubuseruiuxdanapi.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.FOLLOWINGS
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.IS_FAV
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.NAME
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.REPO
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion._ID

class DatabaseDB(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "db_user"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME" +
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $NAME TEXT NOT NULL," +
                " $USERNAME TEXT NOT NULL," +
                " $COMPANY TEXT NOT NULL," +
                " $AVATAR TEXT NOT NULL," +
                " $FOLLOWERS INTEGER NOT NULL," +
                " $FOLLOWINGS INTEGER NOT NULL," +
                " $REPO INTEGER NOT NULL," +
                " $IS_FAV BOOLEAN NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}