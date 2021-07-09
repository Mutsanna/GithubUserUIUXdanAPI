package com.mutsanna.githubuseruiuxdanapi.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion._ID
import java.sql.SQLException

class UserDB(context: Context) {
    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dbHelper: DatabaseDB
        private lateinit var database: SQLiteDatabase
        private var INSTANCE: UserDB? = null

        fun getInstance(context: Context): UserDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserDB(context)
            }
    }

    init {
        dbHelper = DatabaseDB(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()

        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryByName(username: String): Cursor{
        return database.query(
            TABLE_NAME,
            null,
            "$USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteByUsername(username: String): Int {
        return database.delete(DATABASE_TABLE,"$USERNAME = ?", arrayOf(username))
    }
}