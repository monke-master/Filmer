package ru.monke.filmer.domain

import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val showsRepository: ShowRepository
) {

    suspend fun execute(query: String): Result<List<Show>> {
        return showsRepository.searchShows(country = null, title = query)
    }
}