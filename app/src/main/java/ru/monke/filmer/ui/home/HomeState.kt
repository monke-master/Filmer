package ru.monke.filmer.ui.home

import ru.monke.filmer.domain.Show

data class HomeState(
    val freshShows: List<Show> = emptyList(),
    val topShows: List<Show> = emptyList(),
    val isLoading: Boolean = false,
    val exception: Throwable? = null,
)