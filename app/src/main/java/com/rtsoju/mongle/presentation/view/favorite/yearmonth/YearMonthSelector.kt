package com.rtsoju.mongle.presentation.view.favorite.yearmonth

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.YearMonth

class YearMonthSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var selectedIndex: Int = -1
    private val listAdapter: YearMonthListAdapter = YearMonthListAdapter(this::select)
    private var selectionChangedListener: OnSelectionChanged? = null


    init {
        adapter = listAdapter
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    }


    /**
     * @param list 반드시 오름차순으로 정렬되어있어야 한다.
     */
    fun setYearMonthList(list: List<YearMonth>) {
        listAdapter.set(list.mapIndexed { index, yearMonth ->
            YearMonthItem(yearMonth, index, false)
        })
        Log.d("YearMonthSelector", "SET YearMonth: ${list.joinToString()}")
        selectedIndex = -1
        setSelection(YearMonth.now())
    }

    private fun select(idx: Int) {
        Log.d("YearMonthSelector", "Try select (from $selectedIndex) $idx")
        if (selectedIndex != idx) {
            listAdapter.select(selectedIndex, idx)
            selectedIndex = idx
            selectionChangedListener?.onChange(getSelection())
        }
    }

    /**
     * [yearMonth]와 가장 가까운 달이 선택된다. 아무런 list에 yearMonth가 없다면 아무 일도 안생긴다.
     */
    fun setSelection(yearMonth: YearMonth?) {
        val cnt = listAdapter.itemCount
        if (cnt == 0 || getSelection() == yearMonth) {
            return
        }

        if (yearMonth == null) {
            select(-1)
            return
        }

        var low = 0
        var high = cnt - 1
        while (low <= high) {
            val mid = (high - low) / 2 + low
            val midData = listAdapter.getAt(mid)
            if (midData == yearMonth) {
                select(mid)
                return
            } else if (midData < yearMonth) {
                low = mid + 1
            } else {
                high = mid - 1
            }
        }
        select(high)
    }

    fun getSelection(): YearMonth? {
        return if (listAdapter.isInIndices(selectedIndex)) {
            listAdapter.getAt(selectedIndex)
        } else {
            null
        }
    }

    fun setOnSelectionChanged(listener: OnSelectionChanged?) {
        selectionChangedListener = listener
    }

    fun interface OnSelectionChanged {
        fun onChange(selection: YearMonth?)
    }
}

@BindingAdapter("selection")
fun setSelection(view: YearMonthSelector, yearMonth: YearMonth?) {
    Log.d("YearMonthSelector", "SET Selection (from ${view.getSelection()}): $yearMonth")
    view.setSelection(yearMonth)
}

@InverseBindingAdapter(attribute = "selection")
fun getSelection(view: YearMonthSelector): YearMonth? {
    Log.d("YearMonthSelector", "Get Selection: ${view.getSelection()}")
    return view.getSelection()
}

@BindingAdapter("selectionAttrChanged")
fun selectionAttrChanged(view: YearMonthSelector, listener: InverseBindingListener) {
    view.setOnSelectionChanged {
        listener.onChange()
    }
}