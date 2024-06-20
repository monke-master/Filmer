package ru.monke.filmer.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.theme.BlueAccent
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.Grey
import ru.monke.filmer.ui.theme.MontserratFamily
import ru.monke.filmer.ui.theme.Orange
import ru.monke.filmer.ui.theme.TransparentGrey
import ru.monke.filmer.ui.theme.White


@Composable
fun ShowsList(
    shows: List<Show>,
    title: String,
) {
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(id = R.string.see_all),
            style = MaterialTheme.typography.headlineMedium,
            color = BlueAccent
        )
    }

    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(shows) { movie ->
            ShowItem(show = movie)
        }
    }
}

@Composable
private fun ShowItem(
    show: Show
) {
    Column(
        Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .wrapContentHeight(),
    ) {
        ShowCover(
            cover = painterResource(id = R.drawable.ic_launcher_background),
            rating = (show.rating / 10f).toString()
        )
        Text(
            modifier = Modifier.padding(top = 12.dp, start = 8.dp),
            text = show.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.headlineMedium,
            color = White
        )
        Text(
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, start = 8.dp),
            text = show.category,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            color = Grey
        )
    }
}

@Composable
private fun ShowCover(
    cover: Painter,
    rating: String
) {
    Box() {
        Image(
            modifier = Modifier
                .width(135.dp)
                .height(178.dp)
                .clip(RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)),
            painter = cover,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        RatingBadge(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            raring = rating
        )
    }
}

@Preview
@Composable
private fun MovieItemPreview() {
    FilmerTheme(darkTheme = false) {
        ShowItem(show = Show(title = "12 Чаронов", category = "Комедия", 89))
    }
}


