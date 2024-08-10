package ru.monke.filmer.ui.home

import ru.monke.filmer.domain.Show

sealed class HomeState {

    data object Idle: HomeState()

    data object Loading: HomeState()

    data class Error(
        val error: Throwable
    ): HomeState()

    data class Success(
        val freshShows: List<Show>,
        val topShows: List<Show>,
    ): HomeState()
}