package com.dicoding.submissions.githubuserapp_hakam.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.submissions.githubuserapp_hakam.R
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.DetailUserResponse
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_DETAIL
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.TAB_TITLES
import com.dicoding.submissions.githubuserapp_hakam.databinding.ActivityDetailBinding
import com.dicoding.submissions.githubuserapp_hakam.ext.loadImage
import com.dicoding.submissions.githubuserapp_hakam.ui.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val detailUsers: String by lazy {
        intent.extras?.getString(EXTRA_DETAIL, "") ?: ""
    }

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            viewModel.getDetailUser(this, detailUsers)
        }
        initObserver()
        initView()
    }

    private fun initView() {

        // val favoriteUsers = intent.getParcelableExtra<FavoriteEntity>(EXTRA_FAVORITE)

        supportActionBar?.hide()
        binding.apply {

            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, detailUsers)
            TabLayoutMediator(tabsFollows, viewPager.apply {
                adapter = sectionsPagerAdapter
            }) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()

            imbFavorite.setOnClickListener {

            }
        }

    }

    private fun initObserver() {
        viewModel.apply {
            detailUser.observe(this@DetailActivity) { detailUserData ->
                getUserData(detailUserData)
            }

            isLoading.observe(this@DetailActivity) { loader ->
                showLoading(loader)
            }

            getFavoriteUser(
                this@DetailActivity,
                detailUsers
            ).observe(this@DetailActivity) { favoriteUsersList ->
                if (favoriteUsersList) {
                    binding.imbFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.ic_baseline_favorite_24
                        )
                    )
                } else {
                    binding.imbFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            baseContext,
                            R.drawable.ic_favorite_border_before_grey_24
                        )
                    )
                }
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
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}