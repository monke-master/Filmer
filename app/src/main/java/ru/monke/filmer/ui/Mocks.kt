package ru.monke.filmer.ui

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import ru.monke.filmer.R
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Posters
import ru.monke.filmer.domain.Service
import ru.monke.filmer.domain.ServiceImages
import ru.monke.filmer.domain.Show
import java.util.Calendar


val action = Genre(
    id = "1",
    name = "Action"
)

val comedy = Genre(
    id = "2",
    name = "Comedy"
)

val mockedShow = Show(
    id = "123",
    title = "Spider Man: Bomb Ukraine",
    rating = 98,
    duration = 123,
    year = 2024,
    posters = Posters(),
    overview = "For the first time in the cinematic history of Spider-Man, our friendly neighborhood hero's identity is revealed, bringing his Super Hero responsibilities into conflict with his normal life and putting those he cares about most at risk. ",
    genres = listOf(action)
)

fun getMocked(resources: Resources): Show {
    return Show(
        id = "123",
        title = "Spider Man: Bomb Ukraine",
        rating = 98,
        duration = 123,
        year = 2024,
        posters = Posters(),
        overview = "For the first time in the cinematic history of Spider-Man, our friendly neighborhood hero's identity is revealed, bringing his Super Hero responsibilities into conflict with his normal life and putting those he cares about most at risk. ",
        genres = listOf(action)
    )
}

val service = Service(
    id = "1",
    name = "Netflix",
    link = "https://www.amazon.com/gp/video/detail/B001GJ19F4/ref=atv_dp",
    serviceImages = ServiceImages(
        lightThemeImage = "https://media.movieofthenight.com/services/prime/logo-light-theme.svg",
        darkThemeImage = "https://media.movieofthenight.com/services/prime/logo-dark-theme.svg",
        whiteImage = "https://media.movieofthenight.com/services/prime/logo-white.svg"
    )
)