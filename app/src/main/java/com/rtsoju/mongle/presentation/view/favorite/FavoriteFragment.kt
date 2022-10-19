package com.rtsoju.mongle.presentation.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rtsoju.mongle.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

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

        binding.listFavorite.adapter = favoriteListAdapter
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