package com.dicoding.submissions.githubuserapp_hakam.ui.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.di.FavoriteRepository

class FavoriteViewModel() : ViewModel() {

    fun getFavoriteUserList(context: Context): LiveData<List<FavoriteEntity>> {
        val favoriteRepository = FavoriteRepository(context)
        return favoriteRepository.getFavoriteUserList()
    }
}