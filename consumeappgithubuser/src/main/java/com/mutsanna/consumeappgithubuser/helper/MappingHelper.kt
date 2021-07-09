package com.mutsanna.consumeappgithubuser.helper

import android.database.Cursor
import com.mutsanna.consumeappgithubuser.User
import com.mutsanna.consumeappgithubuser.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.mutsanna.consumeappgithubuser.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.mutsanna.consumeappgithubuser.db.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.mutsanna.consumeappgithubuser.db.DatabaseContract.UserColumns.Companion.FOLLOWINGS
import com.mutsanna.consumeappgithubuser.db.DatabaseContract.UserColumns.Companion.NAME
import com.mutsanna.consumeappgithubuser.db.DatabaseContract.UserColumns.Companion.REPO
import com.mutsanna.consumeappgithubuser.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.mutsanna.consumeappgithubuser.db.DatabaseContract.UserColumns.Companion._ID

object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()

        userCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val name = getString(getColumnIndexOrThrow(NAME))
                val company = getString(getColumnIndexOrThrow(COMPANY))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val followers = getInt(getColumnIndexOrThrow(FOLLOWERS))
                val followings = getInt(getColumnIndexOrThrow(FOLLOWINGS))
                val repo = getInt(getColumnIndexOrThrow(REPO))

                val user = User(id, username, name, followers, followings, repo, avatar, company)
                userList.add(user)
            }
        }
        return userList
    }
}