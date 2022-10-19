package com.rtsoju.mongle.presentation.view.daydetail.adapter

import com.bumptech.glide.Glide
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ListitemPhotoBinding
import com.rtsoju.mongle.presentation.base.BaseRecyclerAdapter
import com.rtsoju.mongle.presentation.view.daydetail.model.PhotoPresentationModel

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