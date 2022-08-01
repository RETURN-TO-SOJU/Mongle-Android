package com.won983212.mongle.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.won983212.mongle.databinding.ActivityTutorialBinding

private const val NUM_PAGES = 5

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPagerTutorial.let {
            it.adapter = TutorialSlideAdapter(this)
            binding.dotsIndicator.attachTo(it)
        }
    }

    private class TutorialSlideAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = TutorialPageFragment()
    }
}