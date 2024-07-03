package ru.monke.filmer.data

import ru.monke.filmer.data.local.RequestEntity
import ru.monke.filmer.data.shows.GenreRemote
import ru.monke.filmer.data.shows.Image
import ru.monke.filmer.data.shows.ImageSet
import ru.monke.filmer.data.shows.PaginationResult
import ru.monke.filmer.data.shows.ServiceImageSetRemote
import ru.monke.filmer.data.shows.ShowRemote
import ru.monke.filmer.data.shows.ShowRequest
import ru.monke.filmer.data.shows.ShowResponse
import ru.monke.filmer.data.shows.StreamingOption
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Posters
import ru.monke.filmer.domain.Service
import ru.monke.filmer.domain.ServiceImages
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
        genres = genres.map { it.toDomain() },
        services = getServices()
    )
}

fun ShowRemote.getServices(): List<Service> {
    if (streamingOptions.size == 0) return emptyList()
    val key = streamingOptions.keys.toList()[0]
    return streamingOptions[key]?.map { it.toDomain() }?.distinct() ?: emptyList()
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

fun ShowResponse.toPaginationResult() = PaginationResult(
    items = shows.map { it.toDomain() },
    nextKey = if (hasMore == true) nextCursor else null
)

fun StreamingOption.toDomain() = Service(
    id = service.id,
    name = service.name,
    serviceImages = service.imageSet.toDomain(),
    link = link
)

fun ServiceImageSetRemote.toDomain() = ServiceImages(
    lightThemeImage = lightThemeImage,
    darkThemeImage = darkThemeImage,
    whiteImage = whiteImage
)