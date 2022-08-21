package com.won983212.mongle.presentation.view.main

import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.won983212.mongle.R
import com.won983212.mongle.presentation.base.BaseDataActivity
import com.won983212.mongle.databinding.ActivityMainBinding
import com.won983212.mongle.presentation.view.favorite.FavoriteFragment
import com.won983212.mongle.presentation.view.overview.OverviewFragment
import com.won983212.mongle.presentation.view.setting.SettingFragment

class MainActivity : BaseDataActivity<ActivityMainBinding>() {
    private val viewModel by viewModels<MainViewModel>()

    override val layoutId: Int = R.layout.activity_main

    override fun onInitialize() {
        binding.viewModel = viewModel
        binding.layoutMainContent.adapter = NavAdapter(supportFragmentManager, lifecycle)
        binding.layoutMainContent.registerOnPageChangeCallback(PageChangeListener())
        binding.navOverviewBottom.setOnItemSelectedListener(BottomNavItemSelectedListener())
    }

    private inner class BottomNavItemSelectedListener : NavigationBarView.OnItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.tab_calendar -> {
                    binding.layoutMainContent.currentItem = 0
                    true
                }
                R.id.tab_favorite -> {
                    binding.layoutMainContent.currentItem = 1
                    true
                }
                R.id.tab_settings -> {
                    binding.layoutMainContent.currentItem = 2
                    true
                }
                else -> false
            }
    }

    private inner class PageChangeListener : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.navOverviewBottom.selectedItemId = when (position) {
                0 -> R.id.tab_calendar
                1 -> R.id.tab_favorite
                2 -> R.id.tab_settings
                else -> error("Unknown page id")
            }
        }
    }

    private class NavAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment =
            when (position) {
                0 -> OverviewFragment()
                1 -> FavoriteFragment()
                2 -> SettingFragment()
                else -> error("No Fragment")
            }
    }
}