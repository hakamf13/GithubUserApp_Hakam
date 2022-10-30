package com.dicoding.submissions.githubuserapp_hakam.ui.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.ItemsItem
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_DETAIL
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.USERNAME
import com.dicoding.submissions.githubuserapp_hakam.databinding.FragmentFollowersBinding
import com.dicoding.submissions.githubuserapp_hakam.ui.adapter.ListUserAdapter
import com.dicoding.submissions.githubuserapp_hakam.ui.detail.DetailActivity
import com.dicoding.submissions.githubuserapp_hakam.ui.detail.DetailViewModel
import com.dicoding.submissions.githubuserapp_hakam.ui.favorite.FavoriteViewModelFactory

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

//    private val followersViewModel by viewModels<DetailViewModel>()
    private lateinit var followersViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)

        val factory =

        followersViewModel.followers.observe(viewLifecycleOwner) { followersData ->
            if (followersData == null) {
                val dataUsers = arguments?.getString(USERNAME)?:""
                followersViewModel.getFollowersData(requireActivity(), dataUsers)
            } else {
                showFollowers(followersData)
            }
        }
        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return binding.root
    }

    private fun showFollowers(dataUsers: List<ItemsItem>) {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        val userAdapter = ListUserAdapter(dataUsers)
        binding.rvFollowers.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_DETAIL, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}