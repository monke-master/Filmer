package ru.monke.filmer.ui.showlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.monke.filmer.data.pagination.DefaultPaginator
import ru.monke.filmer.data.pagination.PaginationLoader
import ru.monke.filmer.data.pagination.Paginator
import ru.monke.filmer.data.shows.models.PaginationResult
import ru.monke.filmer.domain.Show

class ShowsListViewModel(
    private val paginationLoader: PaginationLoader
): ViewModel(), ContainerHost<ShowsListState, ShowListSideEffect> {

    override val container = container<ShowsListState, ShowListSideEffect>(ShowsListState())

    private val paginator: Paginator<Show> = DefaultPaginator(
        onLoadItems = this::loadNextInternal,
        onError = {
            intent { reduce { state.copy(error = it, isLoading = false) } }
        },
        onSuccess = { items ->
            intent {
                reduce { state.copy(isLoadingNext = false, items = state.items + items) }
            }
        }
    )

    fun fetchData() {
        viewModelScope.launch {
            intent {
                reduce { state.copy(isLoading = true) }
                val result = paginationLoader.loadNext(null)

                val items = result.getOrElse {
                    reduce { state.copy(error = it) }
                    return@intent
                }
                paginator.setKey(items.nextKey)

                reduce {
                    state.copy(isLoading = false, items = items.items)
                }
            }
        }
    }

    fun loadNext() {
        viewModelScope.launch {
            paginator.loadNext()
        }
    }

    private suspend fun loadNextInternal(cursor: String?): Result<PaginationResult<Show>> {
        intent {
            reduce { state.copy(isLoadingNext = true) }
        }
        return paginationLoader.loadNext(cursor)
    }
}