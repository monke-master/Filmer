package ru.monke.filmer.domain

import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {

    suspend fun execute(): Result<List<Genre>> = showRepository.getGenres()
}