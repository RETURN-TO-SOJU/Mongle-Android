package com.won983212.mongle.domain.usecase.favorite

import com.won983212.mongle.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(id: Int) {
        return favoriteRepository.deleteById(id)
    }
}