package ru.monke.filmer.ui.home

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.monke.filmer.R
import ru.monke.filmer.ui.common.SearchField
import ru.monke.filmer.ui.common.ShowsList
import ru.monke.filmer.ui.mockImageUrl
import ru.monke.filmer.ui.mockedList
import ru.monke.filmer.ui.theme.DarkGrey
import ru.monke.filmer.ui.theme.FilmerTheme
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
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            )
            ImageCarousel(modifier = Modifier.padding(top = 24.dp))
            ShowsList(mockedList, stringResource(id = R.string.most_popular))
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
@Preview(showBackground = true)
private fun HomePreview() {
    FilmerTheme() {
        HomeScreen()
    }
}

