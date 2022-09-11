package com.won983212.mongle.presentation.view.favorite

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.won983212.mongle.presentation.base.AdapterDiffCallback

class FavoriteListAdapter(
    fragment: Fragment,
    private val deleteListener: FavoriteCardFragment.OnDeleteListener?
) : FragmentStateAdapter(fragment) {

    private var data: List<Favorite> = listOf()

    fun set(newList: List<Favorite>) {
        val oldList = this.data
        this.data = newList

        val diffCallback = AdapterDiffCallback(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment =
        FavoriteCardFragment.newInstance(data[position])
            .setOnDeleteListener(deleteListener)
}