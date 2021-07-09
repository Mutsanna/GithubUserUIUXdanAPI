package com.mutsanna.githubuseruiuxdanapi.helper

import android.database.Cursor
import com.mutsanna.githubuseruiuxdanapi.User
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.FOLLOWINGS
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.NAME
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.REPO
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion._ID

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor): ArrayList<User> {
        val userList = ArrayList<User>()

        cursor.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val name = getString(getColumnIndexOrThrow(NAME))
                val company = getString(getColumnIndexOrThrow(COMPANY))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val followers = getInt(getColumnIndexOrThrow(FOLLOWERS))
                val followings = getInt(getColumnIndexOrThrow(FOLLOWINGS))
                val repo = getInt(getColumnIndexOrThrow(REPO))

                userList.add(User(id, username, name, followers, followings, repo, avatar, company))
            }
        }
        return userList
    }
}