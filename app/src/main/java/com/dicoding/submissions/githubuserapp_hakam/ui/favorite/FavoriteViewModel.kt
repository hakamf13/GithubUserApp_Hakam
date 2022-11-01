package com.dicoding.submissions.githubuserapp_hakam.ui.favorite

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.di.FavoriteRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val mFavRepo: FavoriteRepository = FavoriteRepository(application)
    val favUserList: LiveData<List<FavoriteEntity>> = mFavRepo.getFavoriteUserList()
//    fun getFavoriteUserList(context: Context): LiveData<List<FavoriteEntity>> {
//        val favoriteRepository = FavoriteRepository(context)
//        return favoriteRepository.getFavoriteUserList()
//    }
}