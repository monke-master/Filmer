package ru.monke.filmer.domain

import javax.inject.Inject

class GetFreshShowsUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {

    suspend fun execute(year: Int) = showRepository.getFreshShows(year)
}