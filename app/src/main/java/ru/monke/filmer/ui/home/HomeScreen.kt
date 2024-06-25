package ru.monke.filmer.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import org.orbitmvi.orbit.compose.collectAsState
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.common.LoadingIndicator
import ru.monke.filmer.ui.common.SearchField
import ru.monke.filmer.ui.common.ShowsList
import ru.monke.filmer.ui.common.homeViewModel
import ru.monke.filmer.ui.mockImageUrl
import ru.monke.filmer.ui.theme.DarkGrey
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.White

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    LaunchedEffect(key1 = null) {
        viewModel.fetchData()
        Log.d("HomeScreen", "HomeScreen: ")
    }

    val state by viewModel.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (state.isLoading) {
            LoadingIndicator()
        } else if (state.exception != null) {
            Text(text = "Ты обосрался")
        } else {
            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp)
            ) {
                SearchTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                FreshShowsCarousel(modifier = Modifier.padding(top = 24.dp), showsList = state.freshShows)
                ShowsList(state.topShows, stringResource(id = R.string.most_popular))
            }
        }
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
        }
    ) {
        text = it
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreshShowsCarousel(
    modifier: Modifier = Modifier,
    showsList: List<Show>
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
            show = showsList[i]
        )
    }
}

@Composable
fun CarouselItem(
    modifier: Modifier,
    show: Show
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = modifier,
            contentDescription = show.toString(),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(LocalContext.current)
                .data(show.posters.horizontalPoster)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .placeholder(R.drawable.example_show)
                .build(),
            onError = {
                it.result.throwable.printStackTrace()
                Log.d("ERROR", "CarouselItem: " + it.result.toString())
            }
        )
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
        HomeScreen(homeViewModel())
    }
}

