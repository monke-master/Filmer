package ru.monke.filmer.ui.show

import ru.monke.filmer.domain.Show

data class ShowState(
    val show: Show? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false
)