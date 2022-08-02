package com.won983212.mongle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.won983212.mongle.databinding.ActivityTestBinding
import com.won983212.mongle.password.PasswordActivity
import com.won983212.mongle.tutorial.TutorialActivity

class TestActivity : AppCompatActivity() {
    private val listItems = arrayOf(
        "로그인" to LoginActivity::class.java,
        "이용 약관" to AgreeActivity::class.java,
        "비밀번호 입력" to PasswordActivity::class.java,
        "계정 연동" to IntegrationFragment::class.java,
        "튜토리얼" to TutorialActivity::class.java,
        "찜 추가" to NewFavoriteFragment::class.java,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listItems.map { it.first }.toTypedArray()
        binding.listview.let {
            it.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
            it.setOnItemClickListener { parent, view, position, id ->
                val cls = listItems[position].second
                if (Activity::class.java.isAssignableFrom(cls)) {
                    startActivity(Intent(this, cls))
                } else if (BottomSheetDialogFragment::class.java.isAssignableFrom(cls)) {
                    val bottomSheet = cls.newInstance() as BottomSheetDialogFragment
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                } else {
                    Log.e("OnItemClickListener", "Unknown class type: $cls")
                }
            }
        }
    }
}