package com.won983212.mongle.view.daydetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.won983212.mongle.R
import com.won983212.mongle.common.Emotion

class AnalyzedEmotionListAdapter(
    private val analyzedEmotions: Map<Emotion, Int>
) : RecyclerView.Adapter<AnalyzedEmotionListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textProportion: TextView
        val imageEmotion: ImageView

        init {
            textProportion = view.findViewById(R.id.text_listitem_analyzed_proportion)
            imageEmotion = view.findViewById(R.id.image_listitem_analyzed_emotion)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_analyzed_emotion, parent, false)
        view.layoutParams.width = parent.measuredWidth / itemCount
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emotion = Emotion.values()[position]
        val text = holder.textProportion.context.resources
            .getString(R.string.percent_template, analyzedEmotions[emotion].toString())
        holder.textProportion.text = text
        holder.imageEmotion.setImageResource(emotion.bigIconRes)
    }

    override fun getItemCount(): Int = analyzedEmotions.size
}