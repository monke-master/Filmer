package ru.monke.filmer.ui.search.result

import ru.monke.filmer.domain.Show

data class ViewState(
    val fieldInput: String = "",
    val showKeyboard: Boolean = true
)

sealed class DataState {

    data object Idle: DataState()

    data object Loading: DataState()

    data class Error(
        val error: Throwable
    ): DataState()

    data class Success(
        val result: List<Show>,
        val isLoadingNext: Boolean
    ): DataState()
}
