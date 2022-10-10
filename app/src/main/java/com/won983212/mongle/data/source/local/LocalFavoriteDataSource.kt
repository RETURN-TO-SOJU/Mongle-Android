package com.won983212.mongle.data.source.local

import com.won983212.mongle.data.db.AppDatabase
import com.won983212.mongle.data.db.entity.FavoriteEntity
import java.time.YearMonth
import javax.inject.Inject

internal class LocalFavoriteDataSource @Inject constructor(
    private val db: AppDatabase
) {
    suspend fun getAll(): List<FavoriteEntity> {
        return db.favoriteDao().getAll()
    }

    suspend fun getRange(yearMonth: YearMonth): List<FavoriteEntity> {
        val fromEpochDay = yearMonth.atDay(1).toEpochDay()
        val toEpochDay = yearMonth.atEndOfMonth().toEpochDay()
        return db.favoriteDao().getRange(fromEpochDay, toEpochDay)
    }

    suspend fun insert(favorite: FavoriteEntity) {
        db.favoriteDao().insert(favorite)
    }

    suspend fun deleteById(userId: Long) {
        db.favoriteDao().deleteById(userId)
    }

    suspend fun clear() {
        db.favoriteDao().clear()
    }
}