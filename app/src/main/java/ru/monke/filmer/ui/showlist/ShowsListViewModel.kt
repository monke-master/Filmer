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

    override val container = container<ShowsListState, ShowListSideEffect>(ShowsListState.Idle)

    init {
        fetchData()
    }

    private val paginator: Paginator<Show> = DefaultPaginator(
        onLoadItems = this::loadNextInternal,
        onError = {
            intent { reduce { ShowsListState.Error(it) } }
        },
        onSuccess = { items ->
            intent {
                reduce { ShowsListState.Success(items, false) }
            }
        }
    )

    fun fetchData() {
        viewModelScope.launch {
            intent {
                reduce { ShowsListState.Loading }
                val result = paginationLoader.loadNext(null)

                val items = result.getOrElse {
                    reduce { ShowsListState.Error(it) }
                    return@intent
                }
                paginator.setKey(items.nextKey)

                reduce {
                    ShowsListState.Success(items.items, false)
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
            reduce { ShowsListState.Success(state.itemsOrThrow(), true) }
        }
        return paginationLoader.loadNext(cursor)
    }
}