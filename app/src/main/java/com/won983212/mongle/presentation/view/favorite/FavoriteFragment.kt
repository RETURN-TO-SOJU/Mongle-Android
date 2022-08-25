package com.won983212.mongle.presentation.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.won983212.mongle.databinding.ActivityTutorialBinding

// TODO For 중간점검. Replace it favorite
class FavoriteFragment : Fragment() {

    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ActivityTutorialBinding.inflate(layoutInflater)
        binding.pagerTutorial.let {
            val adapter = TutorialSlideAdapter(this)
            it.adapter = adapter
            binding.indicatorTutorialPagerPage.attachTo(it)
            it.orientation = ViewPager2.ORIENTATION_VERTICAL
        }
        binding.btnTutorialSkip.visibility = View.GONE
        return binding.root
    }
}