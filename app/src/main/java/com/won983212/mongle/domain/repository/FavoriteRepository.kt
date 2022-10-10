package com.won983212.mongle.domain.repository

import com.won983212.mongle.domain.model.Favorite
import java.time.YearMonth

interface FavoriteRepository {
    suspend fun getAll(): List<Favorite>

    suspend fun getRange(yearMonth: YearMonth): List<Favorite>

    suspend fun insert(favorite: Favorite)

    suspend fun deleteById(id: Long)

    suspend fun clear()
}