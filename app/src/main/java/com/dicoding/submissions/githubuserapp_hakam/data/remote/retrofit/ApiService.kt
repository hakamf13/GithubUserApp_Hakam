package com.dicoding.submissions.githubuserapp_hakam.data.remote.retrofit

import com.dicoding.submissions.githubuserapp_hakam.BuildConfig.KEY
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.DetailUserResponse
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.ItemsItem
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: $KEY")
    fun getSearchData(
        @Query("q") query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUserData(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUserData(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowingUserData(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}