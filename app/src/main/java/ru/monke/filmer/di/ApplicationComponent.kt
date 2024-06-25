package ru.monke.filmer.di

import dagger.Component
import ru.monke.filmer.ui.home.HomeViewModel
import ru.monke.filmer.ui.show.ShowViewModel

@Component(
    modules = [
        KtorModule::class,
        ShowModule::class
    ]
)
interface ApplicationComponent {

    fun homeViewModelFactory(): HomeViewModel.Factory

    fun showViewModelFactory(): ShowViewModel.Factory

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent
    }
}