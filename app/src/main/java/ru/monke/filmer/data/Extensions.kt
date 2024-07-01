package ru.monke.filmer.data

import ru.monke.filmer.data.local.RequestEntity
import ru.monke.filmer.data.shows.GenreRemote
import ru.monke.filmer.data.shows.Image
import ru.monke.filmer.data.shows.ImageSet
import ru.monke.filmer.data.shows.ShowRemote
import ru.monke.filmer.data.shows.ShowRequest
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Posters
import ru.monke.filmer.domain.Show

fun ShowRemote.toDomain(): Show {
    return Show(
        id = id,
        title = title,
        duration = runtime,
        overview = overview,
        rating = rating,
        year = getYear(),
        posters = imageSet.toPosters(),
        genres = genres.map { it.toDomain() }
    )
}

fun ShowRemote.getYear(): Int {
    releaseYear?.let { return releaseYear }
    return firstAirYear!!
}

fun ImageSet.toPosters(): Posters {
    return Posters(
        verticalPoster = verticalPoster.getAvailableVerticalPoster(),
        horizontalPoster = horizontalPoster.getAvailableHorizontalPoster()
    )
}

fun Image.getAvailableVerticalPoster(): String? {
    w600?.let { return it }
    w480?.let { return it }
    w720?.let { return it }
    w360?.let { return it }
    w1080?.let { return it }
    w240?.let { return it }
    return w1440
}

fun Image.getAvailableHorizontalPoster(): String? {
    w1080?.let { return it }
    w720?.let { return it }
    w1440?.let { return it }
    w600?.let { return it }
    w480?.let { return it }
    w360?.let { return it }
    return w240
}

fun GenreRemote.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun RequestEntity.toDomain() = ShowRequest(
    lastRequestTime = lastDate,
    genreId = genreId,
    showId = showId
)

fun ShowRequest.toRoomEntity() = RequestEntity(
    genreId = genreId,
    lastDate = lastRequestTime,
    showId = showId
)