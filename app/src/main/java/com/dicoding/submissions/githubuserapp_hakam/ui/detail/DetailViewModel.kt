package com.dicoding.submissions.githubuserapp_hakam.ui.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.DetailUserResponse
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.ItemsItem
import com.dicoding.submissions.githubuserapp_hakam.data.remote.retrofit.ApiConfig
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken
import com.dicoding.submissions.githubuserapp_hakam.di.FavoriteRepository
import com.dicoding.submissions.githubuserapp_hakam.ext.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    private val context: Context
) : ViewModel() {

    private val mFavRepo: FavoriteRepository = FavoriteRepository(context)
    val favUserList: LiveData<List<FavoriteEntity>> = mFavRepo.getFavoriteUserList()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _followers = MutableLiveData<List<ItemsItem>?>(null)
    val followers: LiveData<List<ItemsItem>?> = _followers

    private val _following = MutableLiveData<List<ItemsItem>?>(null)
    val following: LiveData<List<ItemsItem>?> = _following

    fun getDetailUser(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getDetailUserData(dataUsers)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(ConstantToken.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ConstantToken.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowersData(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getFollowersUserData(dataUsers)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(ConstantToken.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(ConstantToken.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowingData(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getFollowingUserData(dataUsers)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(ConstantToken.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ConstantToken.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun insertFavoriteUser(id: Int, username: String, avatar: String) {
        val data = FavoriteEntity(id, username, avatar)
        mFavRepo.insertFavoriteUser(data)
        context.showToast("Success add to favorite")
    }

    fun deleteFavoriteUser(data: FavoriteEntity) {
        mFavRepo.deleteFavoriteUser(data)
        context.showToast("Success delete from favorite")
    }

}