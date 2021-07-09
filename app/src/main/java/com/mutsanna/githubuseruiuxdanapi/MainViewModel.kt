package com.mutsanna.githubuseruiuxdanapi

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.mutsanna.githubuseruiuxdanapi.MainActivity.Companion.TOKEN
import com.mutsanna.githubuseruiuxdanapi.databinding.ActivityMainBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<User>>()

    fun setUser(username : String, binding: ActivityMainBinding) {
        val listItem = ArrayList<User>()
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $TOKEN")
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/search/users?q=$username"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
//                TODO("Not yet implemented")
                try {
                    binding.txStatus.visibility = View.INVISIBLE
                    val result = String(responseBody)
                    val responObject = JSONObject(result)
                    val list = responObject.getJSONArray("items")

                    if (responObject.getInt("total_count") != 0){
                        for (i in 0 until list.length()) {
                            val user = User()
                            user.username = list.getJSONObject(i).getString("login")
                            user.avatar = list.getJSONObject(i).getString("avatar_url")
                            listItem.add(user)
                        }
                    } else {
                        binding.txStatus.setText(R.string.no_user)
                        binding.txStatus.visibility = View.VISIBLE
                    }
                    listUser.postValue(listItem)
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
//                TODO("Not yet implemented")
                error.printStackTrace()
            }
        })
    }

    fun getUser(): MutableLiveData<ArrayList<User>> {
        return listUser
    }
}