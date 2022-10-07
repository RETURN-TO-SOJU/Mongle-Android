package com.won983212.mongle.presentation.view.daydetail.adapter

import com.bumptech.glide.Glide
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ListitemPhotoBinding
import com.won983212.mongle.presentation.base.BaseRecyclerAdapter
import com.won983212.mongle.presentation.view.daydetail.model.PhotoPresentationModel

class PhotoListAdapter :
    BaseRecyclerAdapter<ListitemPhotoBinding, PhotoPresentationModel>() {

    override val itemLayoutId: Int = R.layout.listitem_photo

    override fun bind(binding: ListitemPhotoBinding, item: PhotoPresentationModel) {
        Glide.with(binding.imageListitemPhotoThumbnail)
            .load(item.image)
            .placeholder(R.drawable.glide_placeholder)
            .error(R.drawable.glide_error)
            .fallback(R.drawable.glide_error)
            .into(binding.imageListitemPhotoThumbnail)
        binding.textListitemPhotoTitle.text = item.timeText
    }
}