package ru.monke.filmer.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.monke.filmer.R

sealed class NavigationItem(
    val route: String,
    @DrawableRes val iconId: Int,
    @StringRes val nameId: Int
) {
    data object Home: NavigationItem("home", R.drawable.ic_home, R.string.home)
    data object Search: NavigationItem("search", R.drawable.ic_search, R.string.search)
}