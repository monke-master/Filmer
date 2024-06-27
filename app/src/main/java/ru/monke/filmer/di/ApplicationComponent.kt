package ru.monke.filmer.di

import android.content.Context
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import ru.monke.filmer.ui.home.HomeViewModel
import ru.monke.filmer.ui.search.SearchViewModel
import ru.monke.filmer.ui.show.ShowViewModel

@Component(
    modules = [
        KtorModule::class,
        ShowModule::class,
        PreferencesModule::class
    ]
)
interface ApplicationComponent {

    fun homeViewModelFactory(): HomeViewModel.Factory

    fun showViewModelFactory(): ShowViewModel.Factory

    fun searchViewModelFactory(): SearchViewModel.Factory

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}