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
): ViewModel(), ContainerHost<SearchResultState, SearchResultSideEffect> {

    override val container = container<SearchResultState, SearchResultSideEffect>(SearchResultState())

    private var searchJob: Job? = null
    private val SEARCH_DELAY = 1000L


    fun search(query: String) {
        if (query.isEmpty()) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {searchInternal(query) }
    }

    private suspend fun searchInternal(query: String) {
        delay(SEARCH_DELAY)
        intent {
            reduce { state.copy(isLoading = true) }

            val result = searchUseCase.execute(query).getOrElse {
                reduce { state.copy(error = it, isLoading = false) }
                return@intent
            }

            reduce {
                state.copy(
                    isLoading = false,
                    isSuccessful = true,
                    result = result
                )
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
