package com.won983212.mongle.presentation.view.favorite.yearmonth

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.won983212.mongle.presentation.base.event.OnSelectedListener
import java.time.YearMonth

class YearMonthSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var selectedIndex: Int = -1
    private val listAdapter: YearMonthListAdapter = YearMonthListAdapter(this::select)
    private var selectionChangedListener: OnSelectedListener<YearMonth>? = null


    init {
        adapter = listAdapter
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    }


    fun setYearMonthList(list: List<YearMonth>) {
        listAdapter.set(list.mapIndexed { index, yearMonth ->
            YearMonthItem(yearMonth, index, false)
        })
        if (list.isNotEmpty()) {
            select(0)
        }
    }

    private fun select(idx: Int) {
        if (selectedIndex != idx) {
            listAdapter.select(selectedIndex, idx)
            selectedIndex = idx
            selectionChangedListener?.onSelected(listAdapter.getAt(selectedIndex))
        }
    }

    fun setOnSelectionChangedListener(listener: OnSelectedListener<YearMonth>) {
        selectionChangedListener = listener
    }
}