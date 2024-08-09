package ru.monke.filmer.data.shows


abstract class ApiKeySetter {

    lateinit var key: String
        protected set

    abstract suspend fun nextKey(): Result<Unit>
}