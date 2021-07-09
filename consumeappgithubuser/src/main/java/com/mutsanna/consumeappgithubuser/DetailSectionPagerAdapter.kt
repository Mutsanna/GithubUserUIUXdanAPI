package com.mutsanna.consumeappgithubuser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailSectionPagerAdapter(activity: AppCompatActivity, val username: String): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position+1, username)
    }
}