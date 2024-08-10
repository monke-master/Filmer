package ru.monke.filmer.ui.showlist

import ru.monke.filmer.domain.Show

//data class ShowsListState(
//    val isLoading: Boolean = false,
//    val isSuccess: Boolean = false,
//    val isLoadingNext: Boolean = false,
//    val items: List<Show> = emptyList(),
//    val error: Throwable? = null
//)\


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