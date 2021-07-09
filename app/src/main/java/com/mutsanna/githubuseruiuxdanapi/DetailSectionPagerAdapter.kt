package com.mutsanna.githubuseruiuxdanapi

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailSectionPagerAdapter(activity: AppCompatActivity, val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return 2
    }

    override fun createFragment(position: Int): Fragment {
//        TODO("Not yet implemented")
        return FollowFragment.newInstance(position+1, username)
    }
}