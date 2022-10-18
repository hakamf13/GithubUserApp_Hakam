package com.dicoding.submissions.githubuserapp_hakam.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.ItemsItem
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.SearchResponse
import com.dicoding.submissions.githubuserapp_hakam.data.remote.retrofit.ApiConfig
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.TAG
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.USERNAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _search = MutableLiveData<List<ItemsItem>>()
    val search: LiveData<List<ItemsItem>> = _search

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findItems(context: Context) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getSearchData(USERNAME)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _search.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUser(context: Context, dataUsers: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getSearchData(dataUsers)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _search.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}