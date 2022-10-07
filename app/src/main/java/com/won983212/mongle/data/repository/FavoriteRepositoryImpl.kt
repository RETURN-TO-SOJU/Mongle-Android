package com.won983212.mongle.data.repository

import com.won983212.mongle.data.source.local.model.Favorite
import com.won983212.mongle.data.source.local.LocalFavoriteDataSource
import com.won983212.mongle.domain.repository.FavoriteRepository
import java.time.YearMonth
import javax.inject.Inject

internal class FavoriteRepositoryImpl
@Inject constructor(
    private val localFavoriteDataSource: LocalFavoriteDataSource
) : FavoriteRepository {

    override suspend fun getAll(): List<Favorite> =
        localFavoriteDataSource.getAll()

    override suspend fun getRange(yearMonth: YearMonth): List<Favorite> =
        localFavoriteDataSource.getRange(yearMonth)

    override suspend fun insert(favorite: Favorite) =
        localFavoriteDataSource.insert(favorite)

    override suspend fun deleteById(id: Int) =
        localFavoriteDataSource.deleteById(id)

}