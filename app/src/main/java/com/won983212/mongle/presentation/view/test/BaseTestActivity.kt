package com.won983212.mongle.presentation.view.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.databinding.ActivityTestBinding

interface IScreenInfo {
    val name: String
}

data class FragmentInfo(
    override val name: String,
    val factory: () -> BottomSheetDialogFragment
) : IScreenInfo

data class ActivityInfo(
    override val name: String,
    val cls: Class<out Any>,
    val data: Bundle? = null
) : IScreenInfo

data class ManualInfo(
    override val name: String,
    val task: () -> Unit
) : IScreenInfo

abstract class BaseTestActivity : AppCompatActivity() {
    protected abstract val listItems: Array<IScreenInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listItems.map { it.name }.toTypedArray()
        binding.listTest.let {
            it.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
            it.setOnItemClickListener { _, _, position, _ ->
                when (val screen = listItems[position]) {
                    is ActivityInfo -> {
                        val intent = Intent(this, screen.cls)
                        if (screen.data != null) {
                            intent.putExtras(screen.data)
                        }
                        startActivity(intent)
                    }
                    is FragmentInfo -> {
                        val bottomSheet = screen.factory()
                        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                    }
                    is ManualInfo -> {
                        screen.task()
                    }
                    else -> {
                        Log.e("OnItemClickListener", "Unknown class type: $screen.cls")
                    }
                }
            }
        }
    }
}