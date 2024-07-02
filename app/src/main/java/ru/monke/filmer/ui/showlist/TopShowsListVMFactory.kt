package ru.monke.filmer.ui.showlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.monke.filmer.data.pagination.TopShowsLoader
import javax.inject.Inject

class TopShowsListVMFactory @Inject constructor(
    private val topShowsLoader: TopShowsLoader
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShowsListViewModel(topShowsLoader) as T
    }
}