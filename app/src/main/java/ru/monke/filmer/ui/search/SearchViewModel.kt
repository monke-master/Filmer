package ru.monke.filmer.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.monke.filmer.domain.ALL_GENRE
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.GetGenresUseCase
import ru.monke.filmer.domain.GetShowByIdUseCase
import ru.monke.filmer.domain.GetTodayShowUseCase
import ru.monke.filmer.domain.GetTopShowsUseCase
import ru.monke.filmer.ui.show.ShowViewModel
import javax.inject.Inject

private const val TAG = "SearchViewModel"

class SearchViewModel(
    private val getTodayShowUseCase: GetTodayShowUseCase,
    private val getTopShowsUseCase: GetTopShowsUseCase,
    private val getGenresUseCase: GetGenresUseCase
): ViewModel(), ContainerHost<SearchState, SearchSideEffect> {

    override val container = container<SearchState, SearchSideEffect>(SearchState())

    fun fetchData() {
        intent {
            reduce { state.copy(isLoading = true) }
            val genresDef = viewModelScope.async { getGenresUseCase.execute() }
            val todayShowDef = viewModelScope.async { getTodayShowUseCase.execute(ALL_GENRE) }
            val topShowsDef = viewModelScope.async { getTopShowsUseCase.execute() }

            val genresResult = genresDef.await()
            val todayShowResult = todayShowDef.await()
            val topShowsResult = topShowsDef.await()

            genresResult.onFailure {
                reduce { state.copy(isLoading = false, error = it) }
                return@intent
            }
            todayShowResult.onFailure {
                reduce { state.copy(isLoading = false, error = it) }
                return@intent
            }
            topShowsResult.onFailure {
                reduce { state.copy(isLoading = false, error = it) }
                return@intent
            }

            reduce {
                state.copy(
                    searchData = SearchData(
                        todayShow = todayShowResult.getOrNull()!!,
                        recommendedShows = topShowsResult.getOrNull() ?: emptyList(),
                        genres = listOf(ALL_GENRE) + (genresResult.getOrNull() ?: emptyList())
                    ),
                    isLoading = false,

                )
            }
        }
    }

    fun fetchDataByGenre(genre: Genre) {
        intent {
            reduce { state.copy(isUpdatingData = true) }
            val todayShowDef = viewModelScope.async { getTodayShowUseCase.execute(genre) }

            val todayShowResult = todayShowDef.await()
            todayShowResult.onFailure {
                reduce { state.copy(isLoading = false, error = it) }
                return@intent
            }

            Log.d(TAG, "fetchDataByGenre: " + todayShowResult.toString())

            reduce {
                state.copy(
                    searchData = state.searchData?.copy(
                        todayShow = todayShowResult.getOrNull()!!
                    ),
                    isUpdatingData = false
                )
            }
        }
    }


    class Factory @Inject constructor(
        private val getTodayShowUseCase: GetTodayShowUseCase,
        private val getTopShowsUseCase: GetTopShowsUseCase,
        private val getGenresUseCase: GetGenresUseCase
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(
                getTodayShowUseCase,
                getTopShowsUseCase,
                getGenresUseCase) as T
        }
    }
}