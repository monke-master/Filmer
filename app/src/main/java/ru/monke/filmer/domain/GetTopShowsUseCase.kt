package ru.monke.filmer.domain

import javax.inject.Inject

class GetTopShowsUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {

    suspend fun execute(): Result<List<Show>> {
        return showRepository.getTopShows()
    }
}