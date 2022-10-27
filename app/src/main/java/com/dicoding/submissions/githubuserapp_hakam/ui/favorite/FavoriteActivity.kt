package com.dicoding.submissions.githubuserapp_hakam.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_FAVORITE
import com.dicoding.submissions.githubuserapp_hakam.databinding.ActivityFavoriteBinding
import com.dicoding.submissions.githubuserapp_hakam.di.FavoriteRepository
import com.dicoding.submissions.githubuserapp_hakam.ui.adapter.FavoriteAdapter
import com.dicoding.submissions.githubuserapp_hakam.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObserver()
        initView()
    }

    private fun initView() {
        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.apply {

        }
    }

    private fun initObserver() {
        viewModel.apply {
            getFavoriteUserList(this@FavoriteActivity).observe(this@FavoriteActivity) { favorite ->
                if (favorite.isEmpty()) {
                    binding.rvFavorite.visibility = View.GONE
                } else {
                    binding.rvFavorite.visibility = View.VISIBLE
                    showFavoriteUserList(favorite)
                }
            }
        }
    }

    private fun showFavoriteUserList(favorite: List<FavoriteEntity>) {
        val favoriteUserAdapter = FavoriteAdapter(favorite, FavoriteRepository(this))
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = favoriteUserAdapter
        favoriteUserAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteEntity) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_FAVORITE, data)
                startActivity(intent)
            }
        })
    }

}