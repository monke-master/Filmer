package ru.monke.filmer.domain

import javax.inject.Inject

class GetTodayShowUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {

    suspend fun execute(genre: Genre): Result<Show> {
        return showRepository.getTodayShow(genre)
    }
}