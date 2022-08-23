package com.won983212.mongle.presentation.view.daydetail.adapter

import android.util.Log
import com.bumptech.glide.Glide
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ListitemPhotoBinding
import com.won983212.mongle.presentation.base.BaseRecyclerAdapter
import com.won983212.mongle.presentation.view.daydetail.model.Photo

class PhotoListAdapter :
    BaseRecyclerAdapter<ListitemPhotoBinding, Photo>() {

    override val itemLayoutId: Int = R.layout.listitem_photo

    override fun bind(binding: ListitemPhotoBinding, item: Photo) {
        Glide.with(binding.imageListitemPhotoThumbnail)
            .load(item.image)
            .placeholder(R.drawable.glide_placeholder)
            .error(R.drawable.glide_error)
            .fallback(R.drawable.glide_error)
            .into(binding.imageListitemPhotoThumbnail)
        Log.i("GLIDE", item.image)
        binding.textListitemPhotoTitle.text = item.timeText
    }
}