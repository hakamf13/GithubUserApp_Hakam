package com.dicoding.submissions.githubuserapp_hakam.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.submissions.githubuserapp_hakam.R
import com.dicoding.submissions.githubuserapp_hakam.data.local.entity.FavoriteEntity
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.DetailUserResponse
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_DETAIL
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_FAVORITE
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.TAB_TITLES
import com.dicoding.submissions.githubuserapp_hakam.databinding.ActivityDetailBinding
import com.dicoding.submissions.githubuserapp_hakam.ui.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val detailMainViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detailUsers = intent.extras?.get(EXTRA_DETAIL) as String
        val favoriteUsers = intent.extras?.get(EXTRA_FAVORITE) as Boolean
//        val favoriteUsers = intent.getParcelableExtra<FavoriteEntity>(EXTRA_FAVORITE)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, detailUsers)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabsFollow: TabLayout = binding.tabsFollows
        TabLayoutMediator(tabsFollow, viewPager) {tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailMainViewModel.detailUser.observe(this) { detailUserData ->
            getUserData(detailUserData)
        }

        detailMainViewModel.isLoading.observe(this) { loader ->
            showLoading(loader)
        }

        detailMainViewModel.getDetailUser(this, detailUsers)

        detailMainViewModel.favoriteUser.observe(this) { favoriteUsersList ->
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

        binding.imbFavorite.setOnClickListener {
            va
        }
    }

    private fun ImageView.loadImage(avatarUrl: String?) { Glide.with(this.context) .load(avatarUrl) .apply(
        RequestOptions().override(200,200)) .centerCrop() .circleCrop() . into(this) }

    private fun getUserData(userItems: DetailUserResponse){
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}