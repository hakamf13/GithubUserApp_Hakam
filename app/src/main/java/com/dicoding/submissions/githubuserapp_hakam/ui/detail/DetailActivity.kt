package com.dicoding.submissions.githubuserapp_hakam.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissions.githubuserapp_hakam.R
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.DetailUserResponse
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_DETAIL
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_FAVORITE
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

    private lateinit var detailViewModel: DetailViewModel

//    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val detailViewModelFactory = DetailViewModelfactory(this.application, detailUsers)
        detailViewModel = ViewModelProvider(this, detailViewModelFactory)[DetailViewModel::class.java]
        val favorite = intent.getParcelableExtra<FavoriteEntity>(EXTRA_FAVORITE)

//        detailViewModel = ViewModelProvider(this@DetailActivity, detailViewModelFactory)

        if (savedInstanceState == null) {
            detailViewModel.getDetailUser(this, detailUsers)
        }

        initObserver()
        initView()

        binding.apply {

            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, detailUsers)
            TabLayoutMediator(tabsFollows, viewPager.apply {
                adapter = sectionsPagerAdapter
            }) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()

            val getData = detailViewModel.detailUser.value

            imbFavorite.setOnClickListener {
                val insertData = favorite?: FavoriteEntity(getData!!.id, getData.login, getData.avatarUrl)
                if (detailViewModel.getFavoriteUser()!!) {
                    detailViewModel.insertFavoriteUser(insertData)
                } else {
                    detailViewModel.deleteFavoriteUser(insertData)
                }
            }
        }
    }

    private fun initView() {

        // val favoriteUsers = intent.getParcelableExtra<FavoriteEntity>(EXTRA_FAVORITE)

        supportActionBar?.hide()
    }

    private fun initObserver() {
        detailViewModel.apply {
            detailUser.observe(this@DetailActivity) { detailUserData ->
                getUserData(detailUserData)
            }

            isLoading.observe(this@DetailActivity) { loader ->
                showLoading(loader)
            }

            userLogin.observe(this@DetailActivity){ favoriteUsersList ->
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