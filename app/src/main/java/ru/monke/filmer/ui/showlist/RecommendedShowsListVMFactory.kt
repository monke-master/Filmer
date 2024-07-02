package ru.monke.filmer.ui.showlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.monke.filmer.data.pagination.RecommendedShowsLoader
import ru.monke.filmer.domain.Genre
import javax.inject.Inject

class RecommendedShowsListVMFactory @Inject constructor(
    private val loaderFactory: RecommendedShowsLoader.Factory
): ViewModelProvider.Factory {

    fun <T : ViewModel> create(modelClass: Class<T>, genre: Genre): T {
        return (ShowsListViewModel(loaderFactory.create(genre))) as T
    }
}