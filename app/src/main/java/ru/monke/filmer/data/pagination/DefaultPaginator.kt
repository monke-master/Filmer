package ru.monke.filmer.data.pagination

import ru.monke.filmer.data.shows.models.PaginationResult
import javax.inject.Inject

class DefaultPaginator<Item> @Inject constructor(
    private val onLoadItems: suspend (String?) -> Result<PaginationResult<Item>>,
    private val onError: (Throwable) -> Unit,
    private val onSuccess: (List<Item>) -> Unit
) : Paginator<Item> {

    private var currentKey: String? = null
    private var canLoad: Boolean = true
    private var isRequesting: Boolean = false

    override suspend fun loadNext() {
        if (!canLoad || isRequesting) return
        isRequesting = true
        val result = onLoadItems(currentKey)

        val items = result.getOrElse { error ->
            onError(error)
            return
        }

        currentKey = items.nextKey
        if (currentKey == null) {
            canLoad = false
        }
        onSuccess(items.items)
        isRequesting = false
    }

    override suspend fun setKey(key: String?) {
        currentKey = key
        canLoad = currentKey != null
    }
}