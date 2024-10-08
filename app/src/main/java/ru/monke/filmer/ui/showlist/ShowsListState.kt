package ru.monke.filmer.ui.showlist

import ru.monke.filmer.domain.Show

sealed class ShowsListState {

    fun itemsOrThrow() = (this as Success).items

    data object Idle: ShowsListState()

    data object Loading: ShowsListState()

    data class Error(
        val error: Throwable
    ): ShowsListState()

    data class Success(
        val items: List<Show>,
        val isLoadingNext: Boolean
    ): ShowsListState()
}