package com.rtsoju.mongle.presentation.view.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rtsoju.mongle.databinding.FragmentCardFavoriteBinding
import com.rtsoju.mongle.domain.model.Favorite
import com.rtsoju.mongle.presentation.util.getParcelableCompat
import com.rtsoju.mongle.presentation.view.daydetail.DayDetailActivity
import com.rtsoju.mongle.util.DatetimeFormats

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
            binding.imageCardFavoriteEmotion.setOnClickListener {
                Intent(context, DayDetailActivity::class.java).apply {
                    putExtra(DayDetailActivity.EXTRA_DATE, favorite.date)
                    startActivity(this)
                }
            }
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