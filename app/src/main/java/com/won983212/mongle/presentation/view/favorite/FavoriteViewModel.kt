package com.won983212.mongle.presentation.view.favorite

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.presentation.base.BaseViewModel
import java.time.LocalDate
import java.time.YearMonth

class FavoriteViewModel : BaseViewModel() {

    private val _yearMonths = MutableLiveData(
        listOf<YearMonth>(
            YearMonth.of(2022, 8),
            YearMonth.of(2022, 9),
            YearMonth.of(2022, 10),
            YearMonth.of(2022, 11),
            YearMonth.of(2022, 12)
        )
    )
    val yearMonthList = _yearMonths.asLiveData()

    private val _favorites = MutableLiveData(
        listOf(
            Favorite(0, Emotion.HAPPY, LocalDate.of(2022, 8, 20), "기분 좋았던 날!!!"),
            Favorite(1, Emotion.ANXIOUS, LocalDate.of(2021, 11, 20), "불안했던 날인데 이날은?"),
            Favorite(2, Emotion.ANGRY, LocalDate.of(2022, 9, 20), "화났던 날"),
            Favorite(3, Emotion.SAD, LocalDate.of(2021, 8, 31), "SAD SO SAD"),
        )
    )
    val favorites = _favorites.asLiveData()


    fun deleteFavorite(favorite: Favorite) {
        // TODO Implement it
        _favorites.postValue(_favorites.value?.filter { !it.equals(favorites) })
        Log.i("FavoriteViewModel", "DELETE $favorite")
    }

    fun selectYearMonth(yearMonth: YearMonth) {
        // TODO Implement it
        Log.i("FavoriteViewModel", "Select $yearMonth")
    }
}