package ru.monke.filmer.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import ru.monke.filmer.data.ClassProvider
import ru.monke.filmer.data.KtorProvider
import javax.inject.Provider

@Module
interface KtorModule {

    @Binds
    fun bindKtorProvider(i: KtorProvider): ClassProvider<HttpClient>
}