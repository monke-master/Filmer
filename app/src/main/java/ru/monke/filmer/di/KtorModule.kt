package ru.monke.filmer.di

import dagger.Binds
import dagger.Module
import io.ktor.client.HttpClient
import ru.monke.filmer.data.ktor.KtorProvider

@Module
interface KtorModule {

    @Binds
    fun bindKtorProvider(i: KtorProvider): ClassProvider<HttpClient>
}