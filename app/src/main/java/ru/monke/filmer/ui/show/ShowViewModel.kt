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
    private val getShowByIdUseCase: GetShowByIdUseCase,
    private val showId: String
): ViewModel(), ContainerHost<ShowState, ShowSideEffect> {

    override val container = container<ShowState, ShowSideEffect>(ShowState.Idle)

    init {
        getShow()
    }

    private fun getShow() = intent {
        viewModelScope.launch {
            reduce { ShowState.Loading }
            val result = getShowByIdUseCase.execute(showId)

            result
                .onFailure { reduce { ShowState.Error(it) } }
                .onSuccess { reduce { ShowState.Success(it) } }
        }
    }

    class Factory @Inject constructor(
        private val getShowByIdUseCase: GetShowByIdUseCase
    ): ViewModelProvider.Factory {

        fun <T : ViewModel> create(modelClass: Class<T>, showId: String): T {
            return ShowViewModel(getShowByIdUseCase, showId) as T
        }
    }
}