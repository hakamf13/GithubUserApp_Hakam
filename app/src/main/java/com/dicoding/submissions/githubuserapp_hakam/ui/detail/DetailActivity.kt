package com.dicoding.submissions.githubuserapp_hakam.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissions.githubuserapp_hakam.R
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.DetailUserResponse
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_DETAIL
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_FAVORITE
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.TAB_TITLES
import com.dicoding.submissions.githubuserapp_hakam.databinding.ActivityDetailBinding
import com.dicoding.submissions.githubuserapp_hakam.ext.loadImage
import com.dicoding.submissions.githubuserapp_hakam.ext.setImageDrawableExt
import com.dicoding.submissions.githubuserapp_hakam.ui.adapter.SectionsPagerAdapter
import com.dicoding.submissions.githubuserapp_hakam.util.ViewModelfactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val detailUsers: String by lazy {
        intent.extras?.getString(EXTRA_DETAIL, "") ?: ""
    }

    private val favorite : FavoriteEntity by lazy {
        intent.getParcelableExtra<FavoriteEntity>(EXTRA_FAVORITE) as FavoriteEntity
    }

    private val viewModelFactory: ViewModelfactory by lazy {
        ViewModelfactory(this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            if (detailUsers.isNotEmpty()) {
                viewModel.getDetailUser(this, detailUsers)
            } else {
                viewModel.getDetailUser(this, favorite.username)
            }
        }

        initObserver()
        initView()
    }

    private fun initView() {
        supportActionBar?.hide()
    }

    private fun initObserver() {
        viewModel.apply {
            detailUser.observe(this@DetailActivity) { detailUserData ->
                getUserData(detailUserData)
            }
            isLoading.observe(this@DetailActivity) { loader ->
                showLoading(loader)
            }

        }
    }

    private fun getUserData(userItems: DetailUserResponse) {
        binding.apply {
            tvItemUsername.text = userItems.login
            tvItemName.text = userItems.name
            tvItemCompanyValue.text = userItems.company
            tvItemLocationValue.text = userItems.location
            tvRepoValue.text = userItems.publicRepos.toString()
            tvFollowerValue.text = userItems.followers.toString()
            tvFollowingValue.text = userItems.following.toString()
            imgItemAvatar.loadImage(userItems.avatarUrl)

            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, userItems.login)
            TabLayoutMediator(tabsFollows, viewPager.apply {
                adapter = sectionsPagerAdapter
            }) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()

            viewModel.favUserList.observe(this@DetailActivity) { favorite ->
                if (favorite.isNotEmpty()) {
                    val tempUser = favorite.find { it.username == detailUsers }
                    if (tempUser != null) {
                        imbFavorite.setImageDrawableExt(R.drawable.ic_baseline_favorite_24)
                        imbFavorite.setOnClickListener {
                            viewModel.deleteFavoriteUser(tempUser)
                        }
                    } else {
                        imbFavorite.setImageDrawableExt(R.drawable.ic_favorite_border_before_grey_24)
                        imbFavorite.setOnClickListener {
                            viewModel.insertFavoriteUser(
                                userItems.id,
                                userItems.login,
                                userItems.avatarUrl
                            )
                        }
                    }
                } else {
                    imbFavorite.setImageDrawableExt(R.drawable.ic_favorite_border_before_grey_24)
                    imbFavorite.setOnClickListener {
                        viewModel.insertFavoriteUser(
                            userItems.id,
                            userItems.login,
                            userItems.avatarUrl
                        )
                    }
                }
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}