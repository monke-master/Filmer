package ru.monke.filmer.ui.search

import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Show

data class SearchState(
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val recommendedShowsState: RecommendedShowsState? = null,
    val todayShowState: TodayShowState? = null,
    val genres: List<Genre> = emptyList(),
    val isSuccess: Boolean = false
)

data class RecommendedShowsState(
    val shows: List<Show>,
    val isLoading: Boolean = false,
)

data class TodayShowState(
    val todayShow: Show,
    val isLoading: Boolean = false,
)