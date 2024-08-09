package ru.monke.filmer.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.monke.filmer.data.pagination.DefaultPaginator
import ru.monke.filmer.data.pagination.Paginator
import ru.monke.filmer.data.shows.models.PaginationResult
import ru.monke.filmer.domain.ALL_GENRE
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.GetGenresUseCase
import ru.monke.filmer.domain.GetRecommendedShowsUseCase
import ru.monke.filmer.domain.GetTodayShowUseCase
import ru.monke.filmer.domain.Show
import javax.inject.Inject

private const val TAG = "SearchViewModel"

class SearchViewModel(
    private val getTodayShowUseCase: GetTodayShowUseCase,
    private val getRecommendedShowsUseCase: GetRecommendedShowsUseCase,
    private val getGenresUseCase: GetGenresUseCase
): ViewModel(), ContainerHost<SearchState, SearchSideEffect> {

    override val container = container<SearchState, SearchSideEffect>(SearchState())

    private val paginator: Paginator<Show> = DefaultPaginator(
        onLoadItems = this::loadRecommendedShows,
        onError = {
            intent { reduce { state.copy(error = it, isLoading = false) } }
        },
        onSuccess = {
            intent {
                reduce {
                    state.copy(
                        recommendedShowsState = RecommendedShowsState(
                            shows = (state.recommendedShowsState?.shows ?: emptyList()) + it
                        ),
                        isLoading = false
                    )
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
                    todayShowState = TodayShowState(todayShow),
                    recommendedShowsState = RecommendedShowsState(recommendedShows.items),
                    genres = listOf(ALL_GENRE) + genres,
                    isLoading = false,
                    isSuccess = true
                )
            }
        }
    }

    fun fetchDataByGenre(genre: Genre) {
        intent {
            reduce { state.copy(selectedGenre = genre) }
            val todayShowDef = viewModelScope.async { loadTodayShow() }
            val recommendedShowsDef = viewModelScope.async { loadNewRecommendedShows() }

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
                    todayShowState = TodayShowState(todayShow = todayShow),
                    recommendedShowsState = RecommendedShowsState(shows = recommendedShows.items)
                )
            }
        }
    }

    private suspend fun loadTodayShow(): Result<Show> {
        intent {
            reduce {
                state.copy(todayShowState = state.todayShowState?.copy(isLoading = true))
            }
        }
        return getTodayShowUseCase.execute(container.stateFlow.value.selectedGenre)
    }

    private suspend fun loadRecommendedShows(cursor: String? = null): Result<PaginationResult<Show>> {
        intent {
            reduce {
                state.copy(recommendedShowsState = state.recommendedShowsState?.copy(isLoading = true))
            }
        }
        return getRecommendedShowsUseCase.execute(cursor, genre = container.stateFlow.value.selectedGenre)
    }

    private suspend fun loadNewRecommendedShows(): Result<PaginationResult<Show>> {
        intent {
            reduce {
                state.copy(
                    recommendedShowsState = RecommendedShowsState(shows = emptyList(), isLoading = true)
                )
            }
        }
        return getRecommendedShowsUseCase.execute(null, genre = container.stateFlow.value.selectedGenre)
    }

    fun loadNextShows() {
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