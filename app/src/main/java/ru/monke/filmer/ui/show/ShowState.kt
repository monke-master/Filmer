package ru.monke.filmer.ui.show

import ru.monke.filmer.domain.Show

sealed class ShowState {

    data object Idle: ShowState()

    data object Loading: ShowState()

    data class Error(
        val error: Throwable
    ): ShowState()

    data class Success(
        val show: Show
    ): ShowState()
}