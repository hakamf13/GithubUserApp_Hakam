package com.dicoding.submissions.githubuserapp_hakam.ui.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.di.FavoriteRepository

class FavoriteViewModel(
    private val context: Context
) : ViewModel() {

    private val mFavRepo: FavoriteRepository = FavoriteRepository(context)
    val favUserList: LiveData<List<FavoriteEntity>> = mFavRepo.getFavoriteUserList()

}