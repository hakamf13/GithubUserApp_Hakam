package com.dicoding.submissions.githubuserapp_hakam.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_FAVORITE
import com.dicoding.submissions.githubuserapp_hakam.databinding.ActivityFavoriteBinding
import com.dicoding.submissions.githubuserapp_hakam.di.FavoriteRepository
import com.dicoding.submissions.githubuserapp_hakam.ui.adapter.FavoriteAdapter
import com.dicoding.submissions.githubuserapp_hakam.ui.detail.DetailActivity
import com.dicoding.submissions.githubuserapp_hakam.util.ViewModelfactory

class FavoriteActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    private val viewModelFactory: ViewModelfactory by lazy {
        ViewModelfactory(this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
    }

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
            favUserList.observe(this@FavoriteActivity) { favorite ->
                Log.d("FavoriteActivity", "initObserver: $favorite")
                binding.progressBar.visibility = View.VISIBLE
                if (favorite.isEmpty()) {
                    binding.progressBar.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
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