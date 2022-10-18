package com.dicoding.submissions.githubuserapp_hakam.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.USERNAME
import com.dicoding.submissions.githubuserapp_hakam.ui.follow.FollowersFragment
import com.dicoding.submissions.githubuserapp_hakam.ui.follow.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FollowersFragment().apply {
                    arguments = Bundle().apply {
                        putString(USERNAME, username)
                    }
                }
            }
            else -> {
                FollowingFragment().apply {
                    arguments = Bundle().apply {
                        putString(USERNAME, username)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int =  2
}