package com.rtsoju.mongle.presentation.view.favorite

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rtsoju.mongle.domain.model.Favorite
import com.rtsoju.mongle.presentation.base.AdapterDiffCallback

class FavoriteListAdapter(
    fragment: Fragment,
    private val deleteListener: FavoriteCardFragment.OnDeleteListener?
) : FragmentStateAdapter(fragment) {

    private val differ = AsyncListDiffer(this, AdapterDiffCallback<Favorite>())

    fun set(newList: List<Favorite>) {
        differ.submitList(newList)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun createFragment(position: Int): Fragment {
        return FavoriteCardFragment.newInstance(differ.currentList[position])
            .setOnDeleteListener(deleteListener)
    }

    // Adapter는 getItemId, containsItem가 구현되어있어야 스스로 fragment를 recreate한다.
    override fun getItemId(position: Int): Long {
        return differ.currentList[position].id
    }

    override fun containsItem(itemId: Long): Boolean {
        return differ.currentList.any { it.id == itemId }
    }
}