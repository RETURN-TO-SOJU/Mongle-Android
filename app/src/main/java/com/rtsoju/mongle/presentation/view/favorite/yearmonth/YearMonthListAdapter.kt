package com.rtsoju.mongle.presentation.view.favorite.yearmonth

import android.annotation.SuppressLint
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rtsoju.mongle.R
import com.rtsoju.mongle.databinding.ListitemYearmonthBinding
import com.rtsoju.mongle.presentation.util.setTextColorRes

class YearMonthListAdapter(
    private val onItemClickListener: (index: Int) -> Unit
) : RecyclerView.Adapter<YearMonthListAdapter.ViewHolder>() {

    open class ViewHolder(
        val binding: ListitemYearmonthBinding
    ) : RecyclerView.ViewHolder(binding.root)


    private var data: MutableList<YearMonthItem> = mutableListOf()


    @SuppressLint("NotifyDataSetChanged")
    fun set(newList: List<YearMonthItem>) {
        this.data.clear()
        this.data.addAll(newList)
        notifyDataSetChanged()
    }

    fun select(oldIndex: Int, newIndex: Int) {
        if (oldIndex in data.indices) {
            data[oldIndex] = data[oldIndex].ofSelected(false)
            notifyItemChanged(oldIndex)
        }
        if (newIndex in data.indices) {
            data[newIndex] = data[newIndex].ofSelected(true)
            notifyItemChanged(newIndex)
        }
    }

    fun getAt(index: Int) = data[index].date

    fun isInIndices(index: Int) = index in data.indices

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListitemYearmonthBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val binding = holder.binding

        Log.d("YearMonthListAdapter", "Bind $position -> selected: ${item.selected}")
        if (item.selected) {
            binding.imageListitemYearmonthBackground.setImageResource(R.drawable.btn_round)
            binding.textListitemYearmonthDate.setTextColorRes(R.color.white)
            binding.textListitemYearmonthDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        } else {
            binding.imageListitemYearmonthBackground.setImageResource(0)
            binding.textListitemYearmonthDate.setTextColorRes(R.color.disabled)
            binding.textListitemYearmonthDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        }

        binding.textListitemYearmonthDate.text = item.getFormattedDate()
        binding.root.setOnClickListener { onItemClickListener(item.index) }
    }

    override fun getItemCount(): Int = data.size
}