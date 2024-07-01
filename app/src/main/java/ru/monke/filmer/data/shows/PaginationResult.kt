package ru.monke.filmer.data.shows

import ru.monke.filmer.domain.Show

data class PaginationResult<Item>(
    val items: List<Item>,
    val nextKey: String?
)