package com.won983212.mongle.data.repository

import com.won983212.mongle.data.mapper.toDomainModel
import com.won983212.mongle.data.mapper.toEntity
import com.won983212.mongle.data.source.local.LocalFavoriteDataSource
import com.won983212.mongle.domain.model.Favorite
import com.won983212.mongle.domain.repository.FavoriteRepository
import java.time.YearMonth
import javax.inject.Inject

internal class FavoriteRepositoryImpl
@Inject constructor(
    private val localFavoriteDataSource: LocalFavoriteDataSource
) : FavoriteRepository {

    override suspend fun getAll(): List<Favorite> {
        return localFavoriteDataSource.getAll().map { it.toDomainModel() }
    }

    override suspend fun getRange(yearMonth: YearMonth): List<Favorite> {
        return localFavoriteDataSource.getRange(yearMonth).map { it.toDomainModel() }
    }

    override suspend fun insert(favorite: Favorite) {
        localFavoriteDataSource.insert(favorite.toEntity())
    }

    override suspend fun deleteById(id: Long) {
        localFavoriteDataSource.deleteById(id)
    }

    override suspend fun clear() {
        localFavoriteDataSource.clear()
    }

}