package com.dicoding.submissions.githubuserapp_hakam.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.databinding.ItemFavoriteUserBinding
import com.dicoding.submissions.githubuserapp_hakam.di.FavoriteRepository

class FavoriteAdapter(
    private val listFavorite: List<FavoriteEntity>,
    private val favoriteRepository: FavoriteRepository
):
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private lateinit var onItemClickCallback: FavoriteAdapter.OnItemClickCallback

    class FavoriteViewHolder(var favoriteBinding: ItemFavoriteUserBinding):
        RecyclerView.ViewHolder(favoriteBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val favoriteBinding = ItemFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(favoriteBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val userFavorite = listFavorite[position]
        holder.apply {
            favoriteBinding.imgItemAvatar.loadImage(userFavorite.avatarUrl)
            favoriteBinding.tvItemUsername.text = userFavorite.username
            favoriteBinding.btnDelete.setOnClickListener {
                favoriteRepository.deleteFavoriteUser(userFavorite)
            }
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listFavorite[absoluteAdapterPosition])
            }

        }
    }

    override fun getItemCount() = listFavorite.size

    private fun ImageView.loadImage(avatarUrl: String?) { Glide.with(this.context) .load(avatarUrl) .apply(
        RequestOptions().override(200,200)) .centerCrop() .circleCrop() .into(this) }

    fun setOnItemClickCallback(onItemClickCallback: FavoriteAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteEntity)
    }
}