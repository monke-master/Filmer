package ru.monke.filmer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.monke.filmer.domain.GetFreshShowsUseCase
import ru.monke.filmer.domain.GetTopShowsUseCase
import java.util.Calendar
import javax.inject.Inject

class HomeViewModel(
    private val getFreshShowsUseCase: GetFreshShowsUseCase,
    private val getTopShowsUseCase: GetTopShowsUseCase,
): ViewModel(), ContainerHost<HomeState, HomeSideEffect> {

    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        fetchData()
    }

    fun fetchData() {
        intent {
            reduce { state.copy(isLoading = true) }

            val topShowsDef = viewModelScope.async { getTopShowsUseCase.execute() }
            val freshShowsDef = viewModelScope.async {
                getFreshShowsUseCase.execute(Calendar.getInstance().get(Calendar.YEAR))
            }

            val topShowsResult = topShowsDef
                .await()
                .onFailure {
                    reduce { state.copy(exception = it, isLoading = false) }
                    return@intent
                }
            val freshShowsResult = freshShowsDef
                .await()
                .onFailure {
                    reduce { state.copy(exception = it, isLoading = false) }
                    return@intent
                }

            reduce {
                state.copy(
                    isLoading = false,
                    topShows = topShowsResult.getOrNull() ?: emptyList(),
                    freshShows = freshShowsResult.getOrNull() ?: emptyList(),
                )
            }
        }
    }


    class Factory @Inject constructor(
        private val getFreshShowsUseCase: GetFreshShowsUseCase,
        private val getTopShowsUseCase: GetTopShowsUseCase
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(getFreshShowsUseCase, getTopShowsUseCase) as T
        }
    }
}