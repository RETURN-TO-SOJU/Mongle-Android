package com.won983212.mongle.presentation.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.won983212.mongle.data.source.local.model.Favorite
import com.won983212.mongle.databinding.FragmentCardFavoriteBinding
import com.won983212.mongle.presentation.util.getParcelableCompat
import com.won983212.mongle.util.DatetimeFormats

class FavoriteCardFragment : Fragment() {

    private lateinit var binding: FragmentCardFavoriteBinding
    private var favorite: Favorite? = null
    private var deleteListener: OnDeleteListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            favorite = it.getParcelableCompat(ARGUMENT_FAVORITE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCardFavoriteBinding.inflate(inflater, container, false)

        favorite?.let { favorite ->
            binding.imageCardFavoriteEmotion.setImageResource(favorite.emotion.iconRes)
            binding.textCardFavoriteDate.text = favorite.date.format(DatetimeFormats.DATE_SHORT_DOT)
            binding.textCardFavoriteTitle.text = favorite.title
            binding.layoutCardFavorite.backgroundTintList =
                resources.getColorStateList(favorite.emotion.colorRes, requireContext().theme)
            binding.btnCardFavoriteDelete.setOnClickListener { deleteListener?.onDelete(favorite) }
        }

        return binding.root
    }

    fun setOnDeleteListener(listener: OnDeleteListener?): FavoriteCardFragment {
        deleteListener = listener
        return this
    }


    fun interface OnDeleteListener {
        fun onDelete(favorite: Favorite)
    }

    companion object {
        private const val ARGUMENT_FAVORITE = "favorite"

        fun newInstance(favorite: Favorite) =
            FavoriteCardFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_FAVORITE to favorite,
                )
            }
    }
}