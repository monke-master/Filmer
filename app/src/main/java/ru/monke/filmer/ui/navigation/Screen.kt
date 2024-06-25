package ru.monke.filmer.ui.navigation

object Params {
    const val SHOW_ID_PARAM = "showId"
}

sealed class Screen(
    val route: String
) {
    data object ShowScreen: Screen(route = "show/${Params.SHOW_ID_PARAM}")
}