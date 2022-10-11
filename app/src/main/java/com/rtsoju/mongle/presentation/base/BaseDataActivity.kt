package com.rtsoju.mongle.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * DataBinding을 사용하는 Activity이다. ViewBinding만 사용한다면, [BaseActivity]를 사용할 것.
 */
abstract class BaseDataActivity<T : ViewDataBinding> : BaseActivity() {

    protected lateinit var binding: T

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        onInitialize()
    }

    open fun onInitialize() {}
}