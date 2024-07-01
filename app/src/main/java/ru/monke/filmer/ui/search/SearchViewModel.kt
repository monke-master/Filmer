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
import ru.monke.filmer.data.pagination.DefaultPaginator
import ru.monke.filmer.data.pagination.Paginator
import ru.monke.filmer.domain.ALL_GENRE
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.GetGenresUseCase
import ru.monke.filmer.domain.GetRecommendedShowsUseCase
import ru.monke.filmer.domain.GetShowByIdUseCase
import ru.monke.filmer.domain.GetTodayShowUseCase
import ru.monke.filmer.domain.GetTopShowsUseCase
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.show.ShowViewModel
import javax.inject.Inject

private const val TAG = "SearchViewModel"

class SearchViewModel(
    private val getTodayShowUseCase: GetTodayShowUseCase,
    private val getRecommendedShowsUseCase: GetRecommendedShowsUseCase,
    private val getGenresUseCase: GetGenresUseCase
): ViewModel(), ContainerHost<SearchState, SearchSideEffect> {

    override val container = container<SearchState, SearchSideEffect>(SearchState())

    private var selectedGenre = ALL_GENRE

    private val paginator: Paginator<Show> = DefaultPaginator(
        onLoadItems = { cursor ->
            intent { reduce { state.copy(isUpdatingData = true) } }
            getRecommendedShowsUseCase.execute(cursor, genre = selectedGenre)
        },
        onError = {
            intent { reduce { state.copy(error = it, isLoading = false) } }
        },
        onSuccess = {
            intent {
                reduce {
                    state.copy(
                        searchData = state.searchData?.copy(
                            recommendedShows = (state.searchData?.recommendedShows ?: emptyList()) + it
                        ),
                        isUpdatingData = false)
                }
            }
        }
    )

    fun fetchData() {
        intent {
            reduce { state.copy(isLoading = true) }
            val genresDef = viewModelScope.async { getGenresUseCase.execute() }
            val todayShowDef = viewModelScope.async { getTodayShowUseCase.execute(ALL_GENRE) }
            val recommendedShowsDef = viewModelScope.async { getRecommendedShowsUseCase.execute(genre = ALL_GENRE) }

            val genres = genresDef.await().getOrElse {
                reduce { state.copy(isLoading = false, error = it) }
                return@intent
            }
            val todayShow = todayShowDef.await().getOrElse {
                reduce { state.copy(isLoading = false, error = it) }
                return@intent
            }
            val recommendedShows = recommendedShowsDef.await().getOrElse {
                reduce { state.copy(isLoading = false, error = it) }
                return@intent
            }

            paginator.setKey(recommendedShows.nextKey)

            reduce {
                state.copy(
                    searchData = SearchData(
                        todayShow = todayShow,
                        genres = listOf(ALL_GENRE) + genres,
                        recommendedShows = recommendedShows.items
                    ),
                    isLoading = false,
                )
            }
        }
    }

    fun fetchDataByGenre(genre: Genre) {
        intent {
            reduce { state.copy(isUpdatingData = true, searchData = state.searchData?.copy(recommendedShows = emptyList())) }
            selectedGenre = genre
            val todayShowDef = viewModelScope.async { getTodayShowUseCase.execute(genre) }
            val recommendedShowsDef = viewModelScope.async { getRecommendedShowsUseCase.execute(genre = genre) }

            val todayShowResult = todayShowDef.await()
            val recommendedShowsResult = recommendedShowsDef.await()

            todayShowResult.onFailure {
                reduce { state.copy(isLoading = false, error = it) }
                return@intent
            }
            recommendedShowsResult.onFailure {
                reduce { state.copy(isLoading = false, error = it) }
                return@intent
            }

            paginator.setKey(recommendedShowsResult.getOrNull()?.nextKey)
            paginator.loadNext()

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

    fun loadShows() {
        viewModelScope.launch {
            paginator.loadNext()
        }
    }

    class Factory @Inject constructor(
        private val getTodayShowUseCase: GetTodayShowUseCase,
        private val getRecommendedShowsUseCase: GetRecommendedShowsUseCase,
        private val getGenresUseCase: GetGenresUseCase
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(
                getTodayShowUseCase,
                getRecommendedShowsUseCase,
                getGenresUseCase) as T
        }
    }
}