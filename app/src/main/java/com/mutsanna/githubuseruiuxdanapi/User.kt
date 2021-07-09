package com.mutsanna.githubuseruiuxdanapi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Int = 0,
    var username: String? = null,
    var name: String? = null,
    var follower: Int = 0,
    var following: Int = 0,
    var repo: Int = 0,
    var avatar: String? = null,
    var company: String? = null
): Parcelable