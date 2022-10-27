package com.dicoding.submissions.githubuserapp_hakam.di

import android.content.Context
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

    fun getFavoriteUserList(): LiveData<List<FavoriteEntity>> = favoriteDao.getFavoriteUserList()

    fun insertFavoriteUser(favorite: FavoriteEntity) {
        executorService.execute {
            favoriteDao.insertFavoriteUser(favorite)
        }
    }

    fun deleteFavoriteUser(favorite: FavoriteEntity) {
        executorService.execute {
            favoriteDao.deleteFavoriteUser(favorite)
        }
    }

    fun getFavoriteUser(username: String): LiveData<Boolean> =
        favoriteDao.isFavoriteUser(username)

}