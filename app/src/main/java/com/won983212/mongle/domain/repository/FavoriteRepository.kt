package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.model.Favorite
import java.time.YearMonth

interface FavoriteRepository {
    suspend fun getAll(): List<Favorite>

    suspend fun getRange(yearMonth: YearMonth): List<Favorite>

    suspend fun insert(favorite: Favorite)

    suspend fun deleteById(id: Int)
}