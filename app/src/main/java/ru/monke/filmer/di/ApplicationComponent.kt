package ru.monke.filmer.di

import dagger.Component
import ru.monke.filmer.ui.home.HomeViewModel

@Component(
    modules = [
        KtorModule::class,
        ShowModule::class
    ]
)
interface ApplicationComponent {

    fun homeViewModelFactory(): HomeViewModel.Factory

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent
    }
}