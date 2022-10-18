package com.dicoding.submissions.githubuserapp_hakam.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.di.FavoriteRepository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)
    val favoriteUser: LiveData<List<FavoriteEntity>> = favoriteRepository.getFavoriteUserList()

//    fun getFavoriteUserList(): LiveData<List<FavoriteEntity>> = favoriteRepository.getFavoriteUserList()
}