package com.dicoding.submissions.githubuserapp_hakam.ui.follow

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissions.githubuserapp_hakam.ui.detail.DetailViewModel
import com.dicoding.submissions.githubuserapp_hakam.ui.favorite.FavoriteViewModel

class FollowViewModelFactory(
    private val application: Application,
    private val username: String,
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(application, username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}