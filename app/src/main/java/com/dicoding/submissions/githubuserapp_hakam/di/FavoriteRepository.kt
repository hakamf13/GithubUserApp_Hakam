package com.dicoding.submissions.githubuserapp_hakam.di

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.data.local.room.FavoriteDao
import com.dicoding.submissions.githubuserapp_hakam.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(context: Context) {
    private var favoriteDao: FavoriteDao
    private var executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getDatabase(context)
        favoriteDao = db.favoriteDao()
    }

    fun getFavoriteUserList(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getFavoriteUserList()
    }

    fun insertFavoriteUser(favorite: FavoriteEntity) {
        executorService.execute {
            favoriteDao.insertFavoriteUser(favorite)
            Log.d("FavoriteRepository", "insertFavoriteUser: $favorite")
        }
    }

    fun deleteFavoriteUser(favorite: FavoriteEntity) {
        Log.d("FavoriteRepository", "deleteFavoriteUser: $favorite")
        executorService.execute {
            favoriteDao.deleteFavoriteUser(favorite)
        }
    }

}