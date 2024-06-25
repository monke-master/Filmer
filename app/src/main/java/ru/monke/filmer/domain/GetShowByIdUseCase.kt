package ru.monke.filmer.domain

import javax.inject.Inject

class GetShowByIdUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {

    suspend fun execute(showId: String) = showRepository.getShowById(showId)

}