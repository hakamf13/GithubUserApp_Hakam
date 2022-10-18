package com.dicoding.submissions.githubuserapp_hakam.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.ItemsItem
import com.dicoding.submissions.githubuserapp_hakam.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private fun ImageView.loadImage(avatarUrl: String?) { Glide.with(this.context) .load(avatarUrl) .apply(
        RequestOptions().override(200,200)) .centerCrop() .circleCrop() .into(this) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val userDetail = listUser[position]
        holder.binding.imgItemAvatar.loadImage(userDetail.avatarUrl)
        holder.binding.tvItemUsername.text = userDetail.login
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.absoluteAdapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}