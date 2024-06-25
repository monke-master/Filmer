package ru.monke.filmer.data

import ru.monke.filmer.domain.Posters
import ru.monke.filmer.domain.Show

fun ShowRemote.toDomain(): Show {
    return Show(
        title = title,
        category = "Action",
        duration = runtime,
        overview = overview,
        rating = rating,
        year = getYear(),
        posters = imageSet.toPosters()
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