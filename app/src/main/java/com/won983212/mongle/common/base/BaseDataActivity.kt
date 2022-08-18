package com.won983212.mongle.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * DataBinding을 사용하는 Activity이다. ViewBinding만 사용한다면, 이 클래스를 상속받지말고
 * 직접 구현해야 함.
 */
abstract class BaseDataActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        onInitialize()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun onInitialize() {}
}