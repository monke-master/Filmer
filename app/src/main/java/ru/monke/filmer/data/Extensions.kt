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
        verticalPoster = verticalPoster.w600,
        horizontalPoster = horizontalPoster.w1080
    )
}