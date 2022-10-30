package com.dicoding.submissions.githubuserapp_hakam.ui.favorite

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoriteViewModelFactory private constructor(private val context: Context):
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel() as T
        }
        throw IllegalArgumentException("Unknown viewModel class: " + modelClass.name)
    }

//    companion object {
//        @Volatile
//        private var INSTANCE: FavoriteViewModelFactory? = null
//
//
//        @JvmStatic
//        fun getInstance(application: Application): FavoriteViewModelFactory {
//            if (INSTANCE == null) {
//                synchronized(FavoriteViewModelFactory::class.java) {
//                    INSTANCE = FavoriteViewModelFactory(application)
//                }
//            }
//            return INSTANCE as FavoriteViewModelFactory
//        }
//    }
}