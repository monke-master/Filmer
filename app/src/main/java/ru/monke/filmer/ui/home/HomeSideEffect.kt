package ru.monke.filmer.ui.home

sealed class HomeSideEffect {
    data class Toast(val text: String) : HomeSideEffect()
}