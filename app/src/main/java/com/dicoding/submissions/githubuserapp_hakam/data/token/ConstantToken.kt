package com.dicoding.submissions.githubuserapp_hakam.data.token

import com.dicoding.submissions.githubuserapp_hakam.BuildConfig
import com.dicoding.submissions.githubuserapp_hakam.R

class ConstantToken {

    companion object {
        const val WAKTU_LOADING: Int = 2000
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val KEY = BuildConfig.KEY
        const val URL = "https://api.github.com/"
        const val USERNAME = "hakamf13"
        const val TAG = "MainViewModel"
        val TAB_TITLES = intArrayOf(
            R.string.tabs_followers,
            R.string.tabs_following
        )
    }
}