package com.won983212.mongle.domain.usecase.favorite

import com.won983212.mongle.domain.model.Favorite
import com.won983212.mongle.domain.repository.FavoriteRepository
import java.time.YearMonth
import javax.inject.Inject

/**
 * 사용자가 저장한 favorites들을 불러온다. yearMonth를 명시할 경우 해당 달의 favorite만 가져온다.
 */
class GetFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(): List<Favorite> {
        return favoriteRepository.getAll()
    }

    suspend operator fun invoke(yearMonth: YearMonth): List<Favorite> {
        return favoriteRepository.getRange(yearMonth)
    }
}