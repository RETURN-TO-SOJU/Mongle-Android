package com.won983212.mongle.view.daydetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.won983212.mongle.common.Emotion
import com.won983212.mongle.databinding.ActivityDayDetailBinding
import com.won983212.mongle.view.daydetail.adapter.AnalyzedEmotionListAdapter
import com.won983212.mongle.view.daydetail.adapter.PhotoListAdapter
import com.won983212.mongle.view.daydetail.adapter.ScheduleListAdapter
import com.won983212.mongle.view.daydetail.model.Photo
import com.won983212.mongle.view.daydetail.model.Schedule

class DayDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDayDetailBinding

    // TODO mocking data
    private val mockAnalyzedEmotions: Map<Emotion, Int> = mutableMapOf(
        Emotion.ANGRY to 15,
        Emotion.ANXIOUS to 20,
        Emotion.SAD to 20,
        Emotion.HAPPY to 15,
        Emotion.TIRED to 10,
        Emotion.NEUTRAL to 20
    )
    private val mockPhotos: List<Photo> = mutableListOf(
        Photo(null, "02:17 PM"),
        Photo(null, "07:10 PM"),
        Photo(null, "01:00 AM"),
        Photo(null, "04:19 AM")
    )
    private val mockSchedules: List<Schedule> = mutableListOf(
        Schedule("기획 발표", "네이버 캘린더", "오후 3:00\n~ 오후 5:00"),
        Schedule("중간 발표", "네이버 캘린더", "오후 1:00\n~ 오후 4:00"),
        Schedule("기말 발표", "구글 캘린더", "오후 4:00\n~ 오후 6:00"),
        Schedule("생일", "구글 캘린더", "하루종일"),
        Schedule("에어컨 수리", "구글 캘린더", "오후 3:00\n~ 오후 5:00"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbarDayDetail)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        initAnalyzedEmotionList()
        initPhotoList()
        initScheduleList()
    }

    private fun initAnalyzedEmotionList() {
        binding.listDayDetailAnalyzedEmotion.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = AnalyzedEmotionListAdapter(mockAnalyzedEmotions)
        }
    }

    private fun initPhotoList() {
        binding.listDayDetailPhoto.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = PhotoListAdapter(mockPhotos)
        }
    }

    private fun initScheduleList() {
        binding.listDayDetailSchedule.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ScheduleListAdapter(mockSchedules)
        }
    }
}