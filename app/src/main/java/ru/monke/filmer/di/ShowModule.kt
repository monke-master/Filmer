package ru.monke.filmer.di

import dagger.Binds
import dagger.Module
import ru.monke.filmer.data.local.RequestLocalDataSource
import ru.monke.filmer.data.local.RequestLocalDataSourceImpl
import ru.monke.filmer.data.shows.ApiKeySetter
import ru.monke.filmer.data.shows.ApiKeySetterImpl
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
    fun bindLocalDataSource(i: RequestLocalDataSourceImpl): RequestLocalDataSource

    @Binds
    fun bindApiKeySetter(i: ApiKeySetterImpl): ApiKeySetter

}