package com.mutsanna.githubuseruiuxdanapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.mutsanna.githubuseruiuxdanapi.databinding.ActivityDetailBinding
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.AVATAR
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.FOLLOWINGS
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.IS_FAV
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.NAME
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.REPO
import com.mutsanna.githubuseruiuxdanapi.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.mutsanna.githubuseruiuxdanapi.db.UserDB
import java.io.IOException

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userDB: UserDB
    private lateinit var user: User
    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
        const val EXTRA_OBJECT = "object"
    }

    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra<User>(EXTRA_OBJECT) as User
        val title = supportActionBar
        title?.title = user.username
        title?.setDisplayHomeAsUpEnabled(true)

        val sectionPagerAdapter = DetailSectionPagerAdapter(this, user.username.toString())
        val viewPager = binding.detailViewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs = binding.detailTabs

        userDB = UserDB.getInstance(applicationContext)
        userDB.open()

        getFavUser(user.username.toString())
        setFavButton(isFav)

        binding.fabFav.setOnClickListener {
            if (isFav) {
                deleteUser(user.username.toString())
                setFavButton(false)
                isFav = false
            } else {
                insertUser()
                setFavButton(true)
                isFav = true
            }
        }

        TabLayoutMediator(tabs, viewPager){
                tab, position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        setLayout(user)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setLayout(user: User) {
        binding.txName.text = user.name
        binding.txCompany.text = user.company
        binding.txRepo.text = user.repo.toString()
        binding.txFollower.text = user.follower.toString()
        binding.txFollowing.text = user.following.toString()
        Glide.with(this)
            .load(user.avatar)
            .into(binding.circleUserDetail)
    }

    private fun insertUser() {
        val values = contentValuesOf(
            USERNAME to user.username,
            NAME to user.name,
            COMPANY to user.company,
            AVATAR to user.avatar,
            FOLLOWERS to user.follower,
            FOLLOWINGS to user.following,
            REPO to user.repo,
            IS_FAV to true
        )
        userDB.insert(values)
        Toast.makeText(this, "Add to Favorite Success", Toast.LENGTH_SHORT).show()
    }

    private fun deleteUser(username: String) {
        userDB.deleteByUsername(username)
        Toast.makeText(this, "Remove from Favorite Success", Toast.LENGTH_SHORT).show()
    }

    private fun getFavUser(username: String) {
        try {
            val cursor = userDB.queryByName(username)
            isFav = cursor.count > 0
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setFavButton(isFav: Boolean) {
        if (isFav) {
            binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

}