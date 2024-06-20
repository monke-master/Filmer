package ru.monke.filmer.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.monke.filmer.R
import ru.monke.filmer.domain.Movie
import ru.monke.filmer.ui.mockImageUrl
import ru.monke.filmer.ui.mockedList
import ru.monke.filmer.ui.theme.BlueAccent
import ru.monke.filmer.ui.theme.DarkGrey
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.Grey
import ru.monke.filmer.ui.theme.SoftBlue
import ru.monke.filmer.ui.theme.White

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp)
        ) {
            SearchTextField(
                modifier = Modifier.fillMaxWidth()
            )
            ImageCarousel(modifier = Modifier.padding(top = 24.dp))
            MostPopularMovies(mockedList)
        }

    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SoftBlue,
            unfocusedContainerColor = SoftBlue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = White,
            focusedPlaceholderColor = Grey,
            unfocusedPlaceholderColor = Grey,
        ),
        value = text,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Grey
            )
        },
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
        maxLines = 1,
        placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
        onValueChange = { text = it },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCarousel(
    modifier: Modifier = Modifier
) {
    val items = listOf(mockImageUrl, mockImageUrl, mockImageUrl, mockImageUrl)
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState(initialItem = 1) { items.count() },
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
            item = items[i]
        )
    }
}

@Composable
fun CarouselItem(
    modifier: Modifier,
    item: String
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = item.toString(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Das Ist Cool",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }

}

@Composable
fun MostPopularMovies(
    movies: List<Movie>
) {
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.most_popular),
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
        items(movies) { movie ->
            MovieItem(movie = movie)
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie
) {
    Column(
        Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .wrapContentHeight(),
    ) {
        Image(
            modifier = Modifier
                .width(135.dp)
                .height(178.dp)
                .clip(RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(top = 12.dp, start = 8.dp),
            text = movie.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, start = 8.dp),
            text = movie.category,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            color = Grey
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun HomePreview() {
    FilmerTheme() {
        HomeScreen()
    }
}
