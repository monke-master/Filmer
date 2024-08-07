package ru.monke.filmer.ui.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.getMocked
import ru.monke.filmer.ui.theme.BlueAccent
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.Grey
import ru.monke.filmer.ui.theme.White


@Composable
fun ShowsList(
    modifier: Modifier = Modifier,
    shows: List<Show>,
    title: String,
    onItemClicked: (Show) -> Unit = {},
    onShowLoad: () -> Unit = {},
    onAllShowsClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
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
            color = BlueAccent,
            modifier = Modifier.clickable { onAllShowsClicked() }
        )
    }

    LazyRow(
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(shows.size) { index ->
            if (index >= shows.size - 1) {
                onShowLoad()
            }
            val show = shows[index]
            SmallShowItem(
                show = show,
                modifier = Modifier.clickable { onItemClicked(show) }
            )
        }
    }
}

@Composable
fun SmallShowItem(
    modifier: Modifier = Modifier,
    show: Show
) {
    Column(
        modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .wrapContentHeight()
            .width(IntrinsicSize.Min),
    ) {
        ShowPoster(show)
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
            text = show.getGenre(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            color = Grey
        )
    }
}

@Composable
private fun ShowPoster(
    show: Show
) {
    Box() {
        ShimmerPoster(
            poster = show.posters.verticalPoster,
            modifier = Modifier
                .width(135.dp)
                .height(178.dp)
                .clip(RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)))
        RatingBadge(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            rating = show.rating.getRating()
        )
    }
}

@Preview
@Composable
private fun MovieItemPreview() {
    FilmerTheme(darkTheme = false) {
        SmallShowItem(show = getMocked(LocalContext.current.resources))
    }
}


