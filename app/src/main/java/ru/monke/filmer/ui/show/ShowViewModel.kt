package ru.monke.filmer.ui.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.monke.filmer.domain.GetShowByIdUseCase
import javax.inject.Inject

class ShowViewModel(
    private val getShowByIdUseCase: GetShowByIdUseCase
): ViewModel(), ContainerHost<ShowState, ShowSideEffect> {

    override val container = container<ShowState, ShowSideEffect>(ShowState())

    fun getShow(showId: String) = intent {
        viewModelScope.launch {
            reduce { state.copy(isLoading = true) }
            val result = getShowByIdUseCase.execute(showId)

            result
                .onFailure { reduce { state.copy(isLoading = false, error = it) } }
                .onSuccess { reduce { state.copy(isLoading = false, show = it) } }
        }
    }

    class Factory @Inject constructor(
        private val getShowByIdUseCase: GetShowByIdUseCase
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ShowViewModel(getShowByIdUseCase) as T
        }
    }
}