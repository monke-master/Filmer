package ru.monke.filmer.data

import ru.monke.filmer.domain.Show

fun ShowRemote.toDomain(): Show {
    return Show(
        title = title,
        category = "Action",
        duration = runtime,
        overview = overview,
        rating = rating,
        year = getYear()
    )
}

fun ShowRemote.getYear(): Int {
    releaseYear?.let { return releaseYear }
    return firstAirYear!!
}