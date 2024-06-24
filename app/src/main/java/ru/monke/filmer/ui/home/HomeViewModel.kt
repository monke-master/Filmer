package ru.monke.filmer.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import ru.monke.filmer.domain.GetTopShowsUseCase
import ru.monke.filmer.domain.Show
import javax.inject.Inject

class HomeViewModel(
    private val getTopShowsUseCase: GetTopShowsUseCase
): ViewModel() {

    var state by mutableStateOf<List<Show>>(listOf())
        private set

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            val result = getTopShowsUseCase.execute()
            result.onSuccess {
                state = it
            }
        }
    }

    class Factory @Inject constructor(
        private val getTopShowsUseCase: GetTopShowsUseCase
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(getTopShowsUseCase) as T
        }
    }
}