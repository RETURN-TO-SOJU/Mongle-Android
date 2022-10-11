package com.rtsoju.mongle.presentation.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendarview.utils.yearMonth
import com.rtsoju.mongle.domain.model.Favorite
import com.rtsoju.mongle.domain.usecase.favorite.DeleteFavoriteUseCase
import com.rtsoju.mongle.domain.usecase.favorite.GetFavoritesUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavorites: GetFavoritesUseCase,
    private val deleteFavoriteById: DeleteFavoriteUseCase
) : BaseViewModel() {

    var selectedYearMonth = MutableLiveData(YearMonth.now())

    /** Must be sorted */
    private val _yearMonths = MutableLiveData(listOf<YearMonth>())
    val yearMonths = _yearMonths.asLiveData()

    private val _favorites = MutableLiveData(listOf<Favorite>())
    val favorites = _favorites.asLiveData()

    val hasData: LiveData<Boolean> = Transformations.map(_yearMonths) {
        it.isNotEmpty()
    }


    fun initialize() = viewModelScope.launch(Dispatchers.IO) {
        loadYearMonths()
        selectedYearMonth.value?.let {
            loadFavorites(it)
        }
    }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        deleteFavoriteById(favorite.id)
        _favorites.postValue(_favorites.value?.filter { it != favorite })
    }

    fun selectYearMonth() = viewModelScope.launch(Dispatchers.IO) {
        selectedYearMonth.value?.let {
            loadFavorites(it)
        }
    }

    private suspend fun loadFavorites(yearMonth: YearMonth) {
        _favorites.postValue(getFavorites(yearMonth))
    }

    private suspend fun loadYearMonths() {
        val yearMonths = mutableListOf<YearMonth>()

        // favoriteRepository.getAll은 반드시 date순으로 정렬되어있어야 한다.
        getFavorites().forEach {
            val yearMonth = it.date.yearMonth
            if (yearMonths.isEmpty() || yearMonths.last() != yearMonth) {
                yearMonths.add(yearMonth)
            }
        }

        _yearMonths.postValue(yearMonths)
    }
}