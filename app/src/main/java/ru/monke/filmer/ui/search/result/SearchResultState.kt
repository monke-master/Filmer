package ru.monke.filmer.ui.search.result

import ru.monke.filmer.domain.Show

data class SearchResultState(
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val result: List<Show> = emptyList(),
    val isLoadingNext: Boolean = false
)