package com.won983212.mongle.data.source.local

import com.won983212.mongle.data.db.AppDatabase
import com.won983212.mongle.data.model.Favorite
import java.time.YearMonth
import javax.inject.Inject

internal class LocalFavoriteDataSource @Inject constructor(
    private val db: AppDatabase
) {
    suspend fun getAll(): List<Favorite> =
        db.favoriteDao().getAll()

    suspend fun getRange(yearMonth: YearMonth): List<Favorite> {
        val fromEpochDay = yearMonth.atDay(1).toEpochDay()
        val toEpochDay = yearMonth.atEndOfMonth().toEpochDay()
        return db.favoriteDao().getRange(fromEpochDay, toEpochDay)
    }

    suspend fun insert(favorite: Favorite) =
        db.favoriteDao().insert(favorite)

    suspend fun deleteById(userId: Int) =
        db.favoriteDao().deleteById(userId)
}