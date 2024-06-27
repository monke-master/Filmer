package ru.monke.filmer.di

import dagger.Binds
import dagger.Module
import ru.monke.filmer.data.shows.PreferencesDataSource
import ru.monke.filmer.data.shows.PreferencesDataSourceImpl
import ru.monke.filmer.data.shows.ShowRemoteDataSource
import ru.monke.filmer.data.shows.ShowRemoteDataSourceImpl
import ru.monke.filmer.data.shows.ShowRepositoryImpl
import ru.monke.filmer.domain.ShowRepository

@Module
interface ShowModule {

    @Binds
    fun bindShowRemoteDataSource(i: ShowRemoteDataSourceImpl): ShowRemoteDataSource

    @Binds
    fun bindShowRemoteRepository(i: ShowRepositoryImpl): ShowRepository

    @Binds
    fun bindPreferencesDataSource(i: PreferencesDataSourceImpl): PreferencesDataSource

}