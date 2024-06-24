package ru.monke.filmer.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.monke.filmer.app.FilmerApp
import ru.monke.filmer.di.DaggerApplicationComponent
import ru.monke.filmer.ui.home.HomeViewModel

@Composable
fun homeViewModel(): HomeViewModel {
    return DaggerApplicationComponent.builder().build().homeViewModelFactory().create(HomeViewModel::class.java)
}