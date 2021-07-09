package com.mutsanna.githubuseruiuxdanapi.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.mutsanna.githubuseruiuxdanapi"
    const val SCHEME = "content"

    class UserColumns : BaseColumns {
        companion object{
            const val TABLE_NAME = "tb_user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val COMPANY = "company"
            const val AVATAR = "avatar"
            const val FOLLOWERS = "followers"
            const val FOLLOWINGS = "followings"
            const val REPO = "repositories"
            const val IS_FAV = "is_fav"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}