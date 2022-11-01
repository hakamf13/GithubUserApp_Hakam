package com.dicoding.submissions.githubuserapp_hakam.ui.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.ItemsItem
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.USERNAME
import com.dicoding.submissions.githubuserapp_hakam.databinding.FragmentFollowingBinding
import com.dicoding.submissions.githubuserapp_hakam.ui.adapter.ListUserAdapter
import com.dicoding.submissions.githubuserapp_hakam.ui.detail.DetailActivity
import com.dicoding.submissions.githubuserapp_hakam.ui.detail.DetailViewModel

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

//    private val followingViewModel by viewModels<DetailViewModel>()
    private lateinit var followingViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)

        val followingFactory = FollowViewModelFactory(this.requireActivity().application, "")
        followingViewModel = ViewModelProvider(this.requireActivity(), followingFactory)[DetailViewModel::class.java]

        followingViewModel.following.observe(viewLifecycleOwner) { followingData ->
            if (followingData == null) {
                val dataUsers = arguments?.getString(USERNAME) ?: ""
                followingViewModel.getFollowingData(requireActivity(), dataUsers)
            } else {
                showFollowing(followingData)
            }
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return binding.root
    }

    private fun showFollowing(dataUsers: List<ItemsItem>) {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        val userAdapter = ListUserAdapter(dataUsers)
        binding.rvFollowing.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(ConstantToken.EXTRA_DETAIL, data.login)
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