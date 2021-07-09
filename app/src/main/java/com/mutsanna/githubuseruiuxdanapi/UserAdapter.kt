package com.mutsanna.githubuseruiuxdanapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mutsanna.githubuseruiuxdanapi.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var mData = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(user: User) {
            with(itemView) {
                binding.txUsername.text = user.username
                Glide.with(context)
                        .load(user.avatar)
                        .into(binding.circleUserAvatar)

                itemView.setOnClickListener { 
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
//        TODO("Not yet implemented")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        TODO("Not yet implemented")
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        try {
            return mData.size
        } catch (e: Exception) {
            return 0
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items : ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }
}