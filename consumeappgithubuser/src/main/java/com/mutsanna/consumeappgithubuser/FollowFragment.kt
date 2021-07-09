package com.mutsanna.consumeappgithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FollowFragment : Fragment() {
    private lateinit var followAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progBar: ProgressBar
    private lateinit var tvStatus: TextView
    private var listFollows: ArrayList<User> = ArrayList<User>()
    private lateinit var mViewModel: DetailViewModel

    companion object{
        private const val ARG_SECTION_NUMBER = "section"
        private const val ARG_USERNAME = "username"
        private val TAG_FOLLOWER = "follower"
        private val TAG_FOLLOWING = "following"

        fun newInstance(index: Int, username: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putString(ARG_USERNAME, username)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_follow, container, false)
        recyclerView = view.findViewById(R.id.rv_follow)
        progBar = view.findViewById(R.id.follows_progresbar)
        tvStatus = view.findViewById(R.id.tv_follow_status)
        mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        showRecycler()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME)
        val index = arguments?.getInt(ARG_SECTION_NUMBER)
        when (index) {
            1 -> {
                mViewModel.setFollows(username, TAG_FOLLOWER)
                getFollows()
            }
            2 -> {
                mViewModel.setFollows(username, TAG_FOLLOWING)
                getFollows()
            }
        }
    }

    private fun getFollows() {
        clearUser()
        mViewModel.getFollows().observe(this, { followItems ->
            if (followItems.isNotEmpty()) {
                followAdapter.setData(followItems)
                showProgressBar(false)
            } else {
                tvStatus.visibility = View.VISIBLE
                showProgressBar(false)
            }
        })
    }

    private fun clearUser() {
        listFollows.clear()
        followAdapter.setData(listFollows)
    }

    private fun showRecycler() {
        followAdapter = UserAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = followAdapter
    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            progBar.visibility = View.VISIBLE
        } else {
            progBar.visibility = View.INVISIBLE
        }
    }
}