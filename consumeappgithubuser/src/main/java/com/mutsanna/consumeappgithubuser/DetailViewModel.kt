package com.mutsanna.consumeappgithubuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.mutsanna.consumeappgithubuser.MainActivity.Companion.TOKEN
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class DetailViewModel: ViewModel() {

    val listFollows = MutableLiveData<ArrayList<User>>()

    private var URL = "url"

    companion object{
        private const val TAG_FOLLOWER = "follower"
    }

    fun setFollows(username: String?, tag: String) {
        val listItem = ArrayList<User>()
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $TOKEN")
        client.addHeader("User-Agent", "request")

        URL = if (tag.equals(TAG_FOLLOWER)) {
            "https://api.github.com/users/$username/followers"
        } else {
            "https://api.github.com/users/$username/following"
        }

        client.get(URL, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val respArray = JSONArray(result)
                    for (i in 0 until respArray.length()) {
                        val user = User()
                        user.username = respArray.getJSONObject(i).getString("login")
                        user.avatar = respArray.getJSONObject(i).getString("avatar_url")
                        listItem.add(user)
                    }
                    listFollows.postValue(listItem)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                error.printStackTrace()
            }
        })
    }

    fun getFollows(): MutableLiveData<ArrayList<User>> {
        return listFollows
    }
}