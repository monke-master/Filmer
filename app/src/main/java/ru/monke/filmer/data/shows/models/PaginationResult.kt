package ru.monke.filmer.data.shows.models

data class PaginationResult<Item>(
    val items: List<Item>,
    val nextKey: String?
)