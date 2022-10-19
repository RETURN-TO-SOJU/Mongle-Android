package com.rtsoju.mongle.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rtsoju.mongle.data.db.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query(
        "SELECT * FROM favoriteentity " +
                "ORDER BY date"
    )
    /** Ordered by date */
    suspend fun getAll(): List<FavoriteEntity>

    @Query(
        "SELECT * FROM favoriteentity " +
                "WHERE date >= :fromEpochDay AND date <= :toEpochDay " +
                "ORDER BY date"
    )
    /** Ordered by date */
    suspend fun getRange(fromEpochDay: Long, toEpochDay: Long): List<FavoriteEntity>

    @Insert
    suspend fun insert(favorite: FavoriteEntity)

    @Query("DELETE FROM favoriteentity WHERE id=:userId")
    suspend fun deleteById(userId: Long)

    @Query("DELETE FROM favoriteentity")
    suspend fun clear()
}