package com.dicoding.submissions.githubuserapp_hakam.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissions.githubuserapp_hakam.R
import com.dicoding.submissions.githubuserapp_hakam.data.remote.response.ItemsItem
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.EXTRA_DETAIL
import com.dicoding.submissions.githubuserapp_hakam.databinding.ActivityMainBinding
import com.dicoding.submissions.githubuserapp_hakam.ui.adapter.ListUserAdapter
import com.dicoding.submissions.githubuserapp_hakam.ui.detail.DetailActivity
import com.dicoding.submissions.githubuserapp_hakam.ui.favorite.FavoriteActivity
import com.dicoding.submissions.githubuserapp_hakam.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.search.observe(this) { searchData ->
            setUserData(searchData)
        }
        mainViewModel.isLoading.observe(this) { loader ->
            showLoading(loader)
        }
        mainViewModel.findItems(this)

    }

    private fun setUserData(userItems: List<ItemsItem>) {
        val userAdapter = ListUserAdapter(userItems)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter
        userAdapter.setOnItemClickCallback( object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_DETAIL, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.appbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser(this@MainActivity, query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                val intentFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intentFavorite)
                true
            }

            R.id.settings -> {
                val intentSetting = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intentSetting)
                true
            }

            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}