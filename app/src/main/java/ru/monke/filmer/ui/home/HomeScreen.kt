package ru.monke.filmer.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.common.LoadingPlaceholder
import ru.monke.filmer.ui.common.SearchField
import ru.monke.filmer.ui.common.ShimmerPoster
import ru.monke.filmer.ui.common.ShowsList
import ru.monke.filmer.ui.common.repeat
import ru.monke.filmer.ui.getMocked
import ru.monke.filmer.ui.theme.DarkGrey
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.White

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onShowItemClicked: (Show) -> Unit,
    toShowsListNav: () -> Unit
) {
    val state by viewModel.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (state.isLoading) {
            LoadingPlaceholder(text = stringResource(id = R.string.loading_shows))
        } else if (state.exception != null) {
            Text(text = "Ты обосрался")
        } else {
            HomeScreenContent(
                freshShows = state.freshShows,
                topShows = state.topShows,
                onShowItemClicked = onShowItemClicked,
                toShowsListNav = toShowsListNav
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    topShows: List<Show>,
    freshShows: List<Show>,
    onShowItemClicked: (Show) -> Unit,
    toShowsListNav: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        FreshShowsCarousel(
            modifier = Modifier.padding(top = 24.dp),
            showsList = freshShows,
            onShowItemClicked = onShowItemClicked)
        ShowsList(
            shows = topShows,
            title = stringResource(id = R.string.most_popular),
            onItemClicked = onShowItemClicked,
            onAllShowsClicked = toShowsListNav
        )
    }
}

@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    SearchField(
        modifier = modifier,
        value = text,
        placeholder = { Text(text = stringResource(id = R.string.search_title_hint)) },
        trailingIcon = {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                VerticalDivider(
                    color = DarkGrey,
                )
                Icon(
                    modifier = Modifier.padding(start = 8.dp),
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = null,
                    tint = White
                )
            }
        },
        onValueChanged = {
            text = it
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreshShowsCarousel(
    modifier: Modifier = Modifier,
    showsList: List<Show>,
    onShowItemClicked: (Show) -> Unit
) {
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState(initialItem = 1) { showsList.count() },
        modifier = modifier
            .fillMaxWidth()
            .height(221.dp),
        preferredItemWidth = 240.dp,
        itemSpacing = 8.dp,
    ) { i ->
        CarouselItem(
            modifier = Modifier
                .height(205.dp)
                .width(240.dp)
                .maskClip(shape = MaterialTheme.shapes.extraLarge),
            show = showsList[i],
            onShowItemClicked = onShowItemClicked
        )
    }
}

@Composable
fun CarouselItem(
    modifier: Modifier,
    show: Show,
    onShowItemClicked: (Show) -> Unit
) {
    Box(
        modifier = modifier.clickable { onShowItemClicked(show) }
    ) {
        ShimmerPoster(poster = show.posters.horizontalPoster, modifier = modifier)
        Text(
            text = show.title,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }

}



@Composable
@Preview(showBackground = true)
private fun HomePreview() {
    FilmerTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            HomeScreenContent(
                topShows = listOf(getMocked(LocalContext.current.resources)).repeat(10),
                freshShows = listOf(getMocked(LocalContext.current.resources)).repeat(10),
                onShowItemClicked = {},
                toShowsListNav = {}
            )
        }
    }
}

