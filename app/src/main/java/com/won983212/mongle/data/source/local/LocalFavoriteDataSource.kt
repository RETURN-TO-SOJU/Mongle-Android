package com.won983212.mongle.data.source.local

import com.won983212.mongle.data.db.AppDatabase
import com.won983212.mongle.data.model.Favorite
import javax.inject.Inject

internal class LocalFavoriteDataSource @Inject constructor(
    private val db: AppDatabase
) {
    suspend fun getAll(): List<Favorite> = db.userDao().getAll()

    suspend fun insert(favorite: Favorite) = db.userDao().insert(favorite)

    suspend fun deleteById(userId: Int) = db.userDao().deleteById(userId)
}