package com.rtsoju.mongle.domain.usecase.favorite

import com.rtsoju.mongle.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(id: Long) {
        return favoriteRepository.deleteById(id)
    }
}