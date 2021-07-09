package com.mutsanna.consumeappgithubuser

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutsanna.consumeappgithubuser.databinding.ActivityMainBinding
import com.mutsanna.consumeappgithubuser.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.mutsanna.consumeappgithubuser.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    companion object{
        const val TOKEN = "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"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Consumer App Submision 3"

        userAdapter = UserAdapter()
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = userAdapter

        loadUserAsync()

        userAdapter.setOnItemClickCallback(object : OnItemClickCallback{
            override fun onItemClicked(data: User) {
                moveToDetail(data)
            }
        })

    }

    private fun moveToDetail(data: User) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_OBJECT, data)
        startActivity(intent)
    }

    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBarr.visibility = View.VISIBLE
            val defferedUser = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val userList = defferedUser.await()
            binding.progressBarr.visibility = View.INVISIBLE

            if (userList.size > 0) {
                userAdapter.setData(userList)
            } else {
                userList.clear()
                userAdapter.setData(userList)
                binding.tvStatusFav.visibility = View.VISIBLE
            }
        }
    }
}