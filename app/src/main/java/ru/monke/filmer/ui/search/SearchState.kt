package ru.monke.filmer.ui.search

import ru.monke.filmer.domain.Show

data class SearchState(
    val todayShow: Show? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false
)