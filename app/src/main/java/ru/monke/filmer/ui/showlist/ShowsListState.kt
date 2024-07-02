package ru.monke.filmer.ui.showlist

import ru.monke.filmer.domain.Show

data class ShowsListState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoadingNext: Boolean = false,
    val items: List<Show> = emptyList(),
    val error: Throwable? = null
)