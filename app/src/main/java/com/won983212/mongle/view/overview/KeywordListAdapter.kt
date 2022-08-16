package com.won983212.mongle.view.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.won983212.mongle.R

internal class KeywordListAdapter(
    private val dataSet: List<String>
) : RecyclerView.Adapter<KeywordListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textKeyword: TextView

        init {
            textKeyword = view.findViewById(R.id.textKeyword)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_keyword, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = holder.textKeyword.context.resources
            .getString(R.string.keyword_template, dataSet[position])
        holder.textKeyword.text = text
    }

    override fun getItemCount(): Int = dataSet.size
}