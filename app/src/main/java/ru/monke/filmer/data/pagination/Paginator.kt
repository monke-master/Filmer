package ru.monke.filmer.data.pagination

interface Paginator<Item> {

    suspend fun loadNext()

    suspend fun setKey(key: String?)
}