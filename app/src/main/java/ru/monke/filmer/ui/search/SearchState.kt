package ru.monke.filmer.ui.search

import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Show

data class SearchState(
    val searchData: SearchData? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isUpdatingData: Boolean = false
)

data class SearchData(
    val todayShow: Show,
    val recommendedShows: List<Show>,
    val genres: List<Genre>
)