package com.won983212.mongle.presentation.view.daydetail.adapter

import com.won983212.mongle.R
import com.won983212.mongle.presentation.base.BaseRecyclerAdapter
import com.won983212.mongle.databinding.ListitemPhotoBinding
import com.won983212.mongle.presentation.view.daydetail.model.Photo

class PhotoListAdapter :
    BaseRecyclerAdapter<ListitemPhotoBinding, Photo>() {

    override val itemLayoutId: Int = R.layout.listitem_photo

    override fun bind(binding: ListitemPhotoBinding, item: Photo) {
        // TODO (DEBUG) 실제 이미지로 변경할 것
        // holder.imageThumbnail.setImageDrawable(photo.image)
        binding.imageListitemPhotoThumbnail.setImageResource(R.drawable.mock_image)
        binding.textListitemPhotoTitle.text = item.timeText
    }
}