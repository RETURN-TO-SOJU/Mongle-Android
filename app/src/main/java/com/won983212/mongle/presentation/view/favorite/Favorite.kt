package com.won983212.mongle.presentation.view.favorite

import com.won983212.mongle.data.model.Emotion
import java.time.LocalDate

data class Favorite(
    val emotion: Emotion,
    val date: LocalDate,
    val title: String
)
