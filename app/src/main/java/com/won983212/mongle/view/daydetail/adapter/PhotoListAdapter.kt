package com.won983212.mongle.view.daydetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.won983212.mongle.R
import com.won983212.mongle.view.daydetail.model.Photo

class PhotoListAdapter(
    private val photos: List<Photo>
) : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageThumbnail: ImageView
        val textTitle: TextView

        init {
            imageThumbnail = view.findViewById(R.id.image_listitem_photo_thumbnail)
            textTitle = view.findViewById(R.id.text_listitem_photo_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]
        // TODO (DEBUG) 실제 이미지로 변경할 것
        // holder.imageThumbnail.setImageDrawable(photo.image)
        holder.imageThumbnail.setImageResource(R.drawable.mock_image)
        holder.textTitle.text = photo.timeText
    }

    override fun getItemCount(): Int = photos.size
}