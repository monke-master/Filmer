package ru.monke.filmer.ui.search.result

import ru.monke.filmer.domain.Show

sealed class SearchResultState {

    data object Idle: SearchResultState()

    data object Loading: SearchResultState()

    data class Error(
        val error: Throwable
    ): SearchResultState()

    data class Success(
        val result: List<Show>,
        val isLoadingNext: Boolean
    ): SearchResultState()
}