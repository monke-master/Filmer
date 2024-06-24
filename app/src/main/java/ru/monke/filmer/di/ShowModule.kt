package ru.monke.filmer.di

import dagger.Binds
import dagger.Module
import ru.monke.filmer.data.ShowRemoteDataSource
import ru.monke.filmer.data.ShowRemoteDataSourceImpl
import ru.monke.filmer.data.ShowRepositoryImpl
import ru.monke.filmer.domain.ShowRepository

@Module
interface ShowModule {

    @Binds
    fun bindShowRemoteDataSource(i: ShowRemoteDataSourceImpl): ShowRemoteDataSource

    @Binds
    fun bindShowRemoteRepository(i: ShowRepositoryImpl): ShowRepository

}