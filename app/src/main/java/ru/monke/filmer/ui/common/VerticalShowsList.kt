package ru.monke.filmer.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.getMocked
import ru.monke.filmer.ui.theme.FilmerTheme

@Composable
fun VerticalShowsList(
    modifier: Modifier = Modifier,
    shows: List<Show>,
    onLoadShow: () -> Unit,
    onShowClicked: (Show) -> Unit,
    isLoading: Boolean
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(shows.size) { index ->
            if (index == shows.size - 1 && !isLoading) {
                onLoadShow()
            }
            ShowItem(
                show = shows[index],
                onItemClicked = onShowClicked
            )
        }

        if (isLoading) {
            item {
                LoadingIndicator(modifier = Modifier.size(48.dp))
            }
        }
    }
}

@Composable
@Preview
fun VerticalShowsListPreview() {
    FilmerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            VerticalShowsList(
                shows = listOf(getMocked(LocalContext.current.resources)).repeat(10),
                onLoadShow = { /*TODO*/ },
                onShowClicked = {},
                isLoading = false
            )
        }
    }
}