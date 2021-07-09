package com.mutsanna.githubuseruiuxdanapi

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.mutsanna.githubuseruiuxdanapi.databinding.ActivityMainBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private var listUser: ArrayList<User> = ArrayList<User>()
    private lateinit var mViewModel: MainViewModel
    companion object{
        const val TOKEN = "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"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.opt_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_usr_name)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showProgresBar(true)
                mViewModel.setUser(query, binding)
                searchView.clearFocus()
                mViewModel.getUser().observe(this@MainActivity, { userItems ->
                    if (userItems != null) {
                        showRecycler(userItems)
                        showProgresBar(false)
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    clearUser()
                    when {
                        newText.isEmpty() -> {
                            binding.txStatus.setText(R.string.search_usr)
                            binding.txStatus.visibility = View.VISIBLE
                            showProgresBar(false)
                        }
                        newText.length >= 5 -> {
                            binding.txStatus.visibility = View.INVISIBLE
                            showProgresBar(true)
                            mViewModel.setUser(newText, binding)
                            mViewModel.getUser().observe(this@MainActivity, { userItems ->
                                if (userItems != null) {
                                    showRecycler(userItems)
                                    showProgresBar(false)
                                }
                            })
                        }
                        else -> {
                            binding.txStatus.visibility = View.INVISIBLE
                            showProgresBar(true)
                        }
                    }
                }
                return false
            }
        })
        return true
    }

    private fun showProgresBar(state: Boolean) {
        if (state) {
            binding.progressBarr.visibility = View.VISIBLE
        } else {
            binding.progressBarr.visibility = View.INVISIBLE
        }
    }

    private fun showRecycler(listUser: ArrayList<User>) {
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = userAdapter

        userAdapter.setData(listUser)

        userAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: User) {
                getDetailUser(data.username.toString())
                Log.d("cek","${data.username}")

            }
        })
    }

    private fun getDetailUser(username : String) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $TOKEN")
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users/$username"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
//                TODO("Not yet implemented")
                try {
                    val result = String(responseBody)
                    Log.d("cek","${result}")
                    val respObj = JSONObject(result)
                    val user = User()
                    user.username = respObj.getString("login")
                    user.repo = respObj.getInt("public_repos")
                    user.company = respObj.getString("company")
                    user.follower = respObj.getInt("followers")
                    user.following = respObj.getInt("following")
                    user.avatar = respObj.getString("avatar_url")
                    user.name = respObj.getString("name")
                    moveDetail(user)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                error?.printStackTrace()
            }
        })
    }

    private fun moveDetail(user: User) {
        val move = Intent(this, DetailActivity::class.java)
        move.putExtra(DetailActivity.EXTRA_OBJECT, user)
        startActivity(move)
    }

    private fun clearUser() {
        listUser.clear()
        userAdapter.setData(listUser)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_setting -> {
                startActivity(Intent(this, SettingPreferenceActivity::class.java))
            }
            R.id.menu_favorite -> {
                startActivity(Intent(this, FavoriteUserActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}