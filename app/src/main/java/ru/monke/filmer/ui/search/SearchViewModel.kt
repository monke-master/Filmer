package ru.monke.filmer.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.monke.filmer.domain.GetShowByIdUseCase
import ru.monke.filmer.domain.GetTodayShowUseCase
import ru.monke.filmer.ui.show.ShowViewModel
import javax.inject.Inject

class SearchViewModel(
    private val getTodayShowUseCase: GetTodayShowUseCase
): ViewModel(), ContainerHost<SearchState, SearchSideEffect> {

    override val container = container<SearchState, SearchSideEffect>(SearchState())

    fun fetchData() {
        viewModelScope.launch {
            intent {
                reduce { state.copy(isLoading = true) }
                val todayShowResult = getTodayShowUseCase.execute()

                todayShowResult.onFailure {
                    reduce { state.copy(isLoading = false, error = it) }
                    return@intent
                }

                reduce { state.copy(todayShow = todayShowResult.getOrNull(), isLoading = false) }
            }
        }
    }


    class Factory @Inject constructor(
        private val getTodayShowUseCase: GetTodayShowUseCase
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(getTodayShowUseCase) as T
        }
    }
}