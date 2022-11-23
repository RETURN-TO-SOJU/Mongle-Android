package com.rtsoju.mongle.presentation.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rtsoju.mongle.databinding.FragmentFavoriteBinding
import com.rtsoju.mongle.presentation.util.dpToPxInt
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel by viewModels<FavoriteViewModel>()
    private lateinit var favoriteListAdapter: FavoriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        favoriteListAdapter = FavoriteListAdapter(this) {
            viewModel.deleteFavorite(it)
        }

        binding.listFavorite.apply {
            adapter = favoriteListAdapter
            offscreenPageLimit = 3

            setPageTransformer { page, position ->
                val pageWidth: Int = measuredWidth - paddingLeft - paddingRight
                val transformPos = (page.left - (scrollX + paddingLeft)).toFloat() / pageWidth
                val normalizedPosition = abs(abs(transformPos) - 1)
                page.alpha = normalizedPosition + 0.3f

                val max = -height / 20
                val padding = width - dpToPxInt(context, 250)

                page.translationX = -padding * 0.8f * position
                if (transformPos < -1) {
                    page.translationY = 0f
                } else if (transformPos <= 1) {
                    page.translationY = max * (1 - abs(transformPos))
                } else {
                    page.translationY = 0f
                }
            }
        }

        binding.indicatorFavoritePagerPage.attachTo(binding.listFavorite)

        viewModel.favorites.observe(viewLifecycleOwner) {
            favoriteListAdapter.set(it)
        }

        viewModel.selectedYearMonth.observe(viewLifecycleOwner) {
            viewModel.selectYearMonth()
        }

        viewModel.initialize()
        return binding.root
    }
}