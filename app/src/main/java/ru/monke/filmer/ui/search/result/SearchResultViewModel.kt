package ru.monke.filmer.ui.search.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.monke.filmer.domain.SearchUseCase
import javax.inject.Inject

class SearchResultViewModel(
    private val searchUseCase: SearchUseCase
): ViewModel(), ContainerHost<DataState, SearchResultSideEffect> {

    override val container = container<DataState, SearchResultSideEffect>(DataState.Idle)

    var viewState = ViewState()

    private var searchJob: Job? = null
    private val SEARCH_DELAY = 1000L
    private lateinit var query: String

    fun retry() {
        search(query)
    }


    fun search(query: String) {
        if (query.isEmpty()) return
        this.query = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {searchInternal(query) }
    }

    private suspend fun searchInternal(query: String) {
        delay(SEARCH_DELAY)
        intent {
            reduce { DataState.Loading }

            val result = searchUseCase.execute(query).getOrElse {
                reduce { DataState.Error(it) }
                return@intent
            }

            reduce {
                DataState.Success(result, false)
            }
        }
    }

    class Factory @Inject constructor(
        private val searchUseCase: SearchUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchResultViewModel(searchUseCase) as T
        }
    }
}
