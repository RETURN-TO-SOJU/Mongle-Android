package com.rtsoju.mongle.presentation.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.rtsoju.mongle.R

internal class BehaviorAlpha(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<ViewGroup>(context, attrs) {

    private val availableHeight: Float

    init {
        val tv = TypedValue()
        availableHeight =
            if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                val actionBarHeight =
                    TypedValue.complexToDimensionPixelSize(
                        tv.data,
                        context.resources.displayMetrics
                    )
                context.resources
                    .getDimension(R.dimen.appbar_height) - actionBarHeight
            } else {
                0f
            }
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ViewGroup,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: ViewGroup,
        dependency: View
    ): Boolean {
        child.alpha = (availableHeight + dependency.y) / availableHeight
        return false
    }
}